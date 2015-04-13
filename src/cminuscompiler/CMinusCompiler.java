package cminuscompiler;

import dataflow.ControlFlowAnalysis;
import dataflow.LivenessAnalysis;
import scanner.CMinusScanner;
import scanner.Token;
import parser.CMinusParser;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.PushbackReader;
import java.util.HashMap;
import lowlevel.CodeItem;
import optimizer.LowLevelCodeOptimizer;
import parser.ParseException;
import parser.Program;
import x64codegen.X64AssemblyGenerator;
import x64codegen.X64CodeGenerator;
import x64codegen.X64RegisterAllocator;
import x86codegen.X86AssemblyGenerator;
import x86codegen.X86CodeGenerator;
import x86codegen.X86RegisterAllocator;

/**
 *
 * @author Jake P
 * @author Nate H
 */
public class CMinusCompiler {

    public static HashMap globalHash = new HashMap();
    private static boolean genX64Code = false;

    public CMinusCompiler() {
    }

    public static void setGenX64Code(boolean useX64) {
        genX64Code = useX64;
    }
    public static boolean getGenX64Code() {
        return genX64Code;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, Exception {
        //First command line argument is input, second is output
        try{
            String filePrefix = args[0];
            String fileName = args[0] + ".c";
            setGenX64Code(true);
            PushbackReader reader = new PushbackReader(new FileReader(fileName));
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePrefix + "Tree.ast"));
            CMinusScanner scanner = new CMinusScanner(reader);
            //These two lines create the parser and start it. As the name 'recursive descent' implies, it works recursively.
            CMinusParser parser = new CMinusParser(scanner);
            Program parseResult = parser.startParse();
            
            //Calls the print routine. This works recursively as well, each Class's print routine will call
            //the appropriate print function on any child objects that it has, or print the data for terminals
            parseResult.printProgram(writer);
            
            CodeItem lowLevelCode = parseResult.genLLCode();

            fileName = filePrefix + ".ll";
            PrintWriter outFile =
                    new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            lowLevelCode.printLLCode(outFile);
            outFile.close();

            int optiLevel = 2;
            LowLevelCodeOptimizer lowLevelOpti =
                    new LowLevelCodeOptimizer(lowLevelCode, optiLevel);
            lowLevelOpti.optimize();

            fileName = filePrefix + ".opti";
            outFile =
                    new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            lowLevelCode.printLLCode(outFile);
            outFile.close();

            if (genX64Code) {
                X64CodeGenerator x64gen = new X64CodeGenerator(lowLevelCode);
                x64gen.convertToX64();
            }
            else {
                X86CodeGenerator x86gen = new X86CodeGenerator(lowLevelCode);
                x86gen.convertToX86();
            }
            fileName = filePrefix + ".x86";
            outFile =
                    new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            lowLevelCode.printLLCode(outFile);
            outFile.close();

//    lowLevelCode.printLLCode(null);

            // simply walks functions and finds in and out edges for each BasicBlock
            ControlFlowAnalysis cf = new ControlFlowAnalysis(lowLevelCode);
            cf.performAnalysis();
//    cf.printAnalysis(null);

            // performs DU analysis, annotating the function with the live range of
            // the value defined by each oper (some merging of opers which define
            // same virtual register is done)
//    DefUseAnalysis du = new DefUseAnalysis(lowLevelCode);
//    du.performAnalysis();
//    du.printAnalysis();

            LivenessAnalysis liveness = new LivenessAnalysis(lowLevelCode);
            liveness.performAnalysis();
            liveness.printAnalysis();

            if (genX64Code) {
                int numRegs = 15;
                X64RegisterAllocator regAlloc = new X64RegisterAllocator(lowLevelCode,
                        numRegs);
                regAlloc.performAllocation();

                lowLevelCode.printLLCode(null);

                fileName = filePrefix + ".s";
                outFile =
                        new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
                X64AssemblyGenerator assembler =
                        new X64AssemblyGenerator(lowLevelCode, outFile);
                assembler.generateX64Assembly();
                outFile.close();
            }
            else {
                int numRegs = 7;
                X86RegisterAllocator regAlloc = new X86RegisterAllocator(lowLevelCode,
                        numRegs);
                regAlloc.performAllocation();

                lowLevelCode.printLLCode(null);

                fileName = filePrefix + ".s";
                outFile =
                        new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
                X86AssemblyGenerator assembler =
                        new X86AssemblyGenerator(lowLevelCode, outFile);
                assembler.generateAssembly();
                outFile.close();
            }

        }
        catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
