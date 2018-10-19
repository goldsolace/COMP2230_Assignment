/**
 * Test IndirectHeap
 * 
 * @author Ashwin Deen Masi
 * @studentID 3163458
 * @lastModified: 19-10-2018
 */
import java.util.*;
//changing original heap into a direct heap. Will start working with an array of ints of 8 length 
public class IndirectHeap
{
	private int Capacity = 8;
	//Array of Values 
	private int[] key;
	//Array of ints which list where the key is placed in the heap
	private int[] into;
	//Array of ints which list from where in the key values are placed in the heap
	private int[] outof;
	//creates empy heap with length of Capacity
	public IndirectHeap()
	{
		
		key = 	new int[Capacity+1];
		into = 	new int[Capacity+1];
		outof = new int[Capacity+1];
		for (int i=1; i<=8; i++) {
			into[i]=i;
			outof[i]=i;
		}
	} 	
	//Take input array and places it into the key. Then will order into and outof to make into a heap 
	public IndirectHeap(int[] array)
	{
		key = array;
		into = 	new int[Capacity+1];
		outof = new int[Capacity+1];
		for (int i=1; i<=8; i++) {
			into[i]=i;
			outof[i]=i;
		}
		Heapify(key,outof,into);
	}
	//makes subtree rooted at i into a heap
	//v is key, O is outof, I is into
	private void Siftdown(int[] v,int[] O, int[] I,int i)
	{
		
		int temp = O[i];
		while (2*i <= Capacity) {
			
			int child = 2*i;
			//If the node has children then see which child is smaller and choose that one
			if (child <Capacity && v[O[child+1]]<v[O[child]])
				child=child+1;
			//If child is smaller then parent rearange into/outof  
			if (v[O[child]] < v[temp]) {
				O[i] = O[child];
				I[O[i]]=i;
			}
			else
				break;
			i = child;
		}
		O[i] = temp;
		I[O[i]]=i;
	}
	//Uses Siftdown to make whole array into a heap
	//v is key O in outof, I is into
	private void Heapify(int[] v, int[] O, int[] I)
	{
		//Run Siftdown for all nodes excluding leaves
		for (int i=Capacity/2; i>=1; i--)
			Siftdown(v,O,I,i);
	}
	public int Smallest()
	{
		return key[outof[1]];
	}
	public static void main(String[] args)
	{
	int[] testArray = new int[9];
	testArray[1]= 28;
	testArray[2]= 12;
	testArray[3]= 312;
	testArray[4]= 25;
	testArray[5]= 8;
	testArray[6]= 109;
	testArray[7]= 7;
	testArray[8]= 18;
	
	IndirectHeap testHeap = new IndirectHeap(testArray);
	System.out.println(Arrays.toString(testHeap.key));
	System.out.println(Arrays.toString(testHeap.into));
	System.out.println(Arrays.toString(testHeap.outof));
	System.out.println(testHeap.Smallest());
	}
}