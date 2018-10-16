import java.util.*;
//testing making a heap. Will start working with an array of ints of 8 length 
public class Heap
{
	private int Capacity = 8;
	//Arrary that will be arranged into a heap
	private int[] heap;
	//creates empy heap with length of Capacity
	public Heap()
	{
		
		heap = new int[Capacity];
	} 	
	//Take input array and reorders it into a heap using Heapify 
	public Heap(int[] array)
	{
		heap = array;
		Heapify(heap);
	}
	//makes subtree rooted at i into a heap
	private void Siftdown(int[] v,int i)
	{
		
		int temp = v[i];
		while (2*i <= Capacity) {
			int child = 2*i;
			if (child <Capacity && v[child+1]>v[child])
				child=child+1;
			if (v[child] > temp)
				v[i] = v[child];
			else
				break;
			i = child;
		}
		v[i] = temp;
	}
	//Uses Siftdown to make whole array into a heap 
	private void Heapify(int[] v)
	{
		for (int i=Capacity/2; i>=1; i--)
			Siftdown(v,i);
	}
}

public static void main(String[] args)
{
	int[] testArray = (66,12,312,25,8,109,7,18);
	Heap testHeap = Heap(testArray);
	System.out.println(Arrays.toString(testHeap.heap))
}