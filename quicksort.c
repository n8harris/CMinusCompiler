void quicksort(int x,int first,int last){
    int pivot;
    int j;
    int temp; 
    int i;

     if(first<last){
         pivot=first;
         i=first;
         j=last;

         while(i<j){
             while(x<=i){
                 i = i + 1;
			}
             while(x>j + j){
                 j = j + 1;
			}
             if(i<j){
                 temp=x;
                  i=j;
                  x=temp;
             }
         } 

         temp=x;
         x=j;
         j=temp;
         quicksort(x,first,j-1);
         quicksort(x,j+1,last);

    } else {
		i = 0;
	}
	
	j = 1;
}
