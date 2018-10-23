/**
 * Test IndirectHeap
 * 
 * @author Ashwin Deen Masi
 * @studentID 3163458
 * @lastModified: 22-10-2018
 */
import java.util.*;
//Indirect heap of intergers. Capacity can be changed we adding/taking away elements and when first array is added. Enqueue and Dequeue.
public class IndirectHeap
{
	private int Capacity= 1;
	//Array of Values 
	private Station[] key;
	//Array of ints which list where the key is placed in the heap
	private int[] into;
	//Array of ints which list from where in the key values are placed in the heap
	private int[] outof;
	//creates empy heap with length of Capacity
	public IndirectHeap()
	{
		
		key = 	new Station[Capacity+1];
		into = 	new int[Capacity+1];
		outof = new int[Capacity+1];
		for (int i=1; i<=Capacity; i++) {
			into[i]=i;
			outof[i]=i;
		}
	} 	
	//Take input array and places it into the key. Then will order into and outof to make into a heap 
	public IndirectHeap(Station[] array)
	{
		key = array;
		Capacity = key.length-1;
		into = 	new int[Capacity+1];
		outof = new int[Capacity+1];
		for (int i=1; i<=Capacity; i++) {
			into[i]=i;
			outof[i]=i;
		}
		Heapify(key,outof,into);
	}
	//makes subtree rooted at i into a heap
	//v is key, O is outof, I is into
	private void Siftdown(Station[] v,int[] O, int[] I,int i)
	{
		
		int temp = O[i];
		while (2*i <= Capacity) {
			
			int child = 2*i;
			//If the node has children then see which child is smaller and choose that one
			if (child <Capacity && v[O[child+1]].compareTo(v[O[child]])<0)
				child=child+1;
			//If child is smaller then parent rearange into/outof  
			if (v[O[child]].compareTo(v[temp])<0) {
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
	private void Heapify(Station[] v, int[] O, int[] I)
	{
		//Run Siftdown for all nodes excluding leaves
		for (int i=Capacity/2; i>=1; i--)
			Siftdown(v,O,I,i);
	}
	//places a new value in the heap then rearranges to keep structure 
	public void Enqueue(Station Value)
	{
		Capacity++;
		Station[] tempKey = new Station[Capacity+1];
		int[] tempInto = new int[Capacity+1];
		int[] tempOutof = new int[Capacity+1];
		for (int i=1; i<=Capacity-1; i++) {
			tempKey[i] = key[i];
			tempInto[i] = into[i];
			tempOutof[i] = outof[i]; 
		}
		tempKey[Capacity] = Value;
		tempInto[Capacity] = Capacity;
		tempOutof[Capacity] = Capacity;
		key = tempKey;
		into = tempInto;
		outof = tempOutof;
		Heapify(key,outof,into);
	}
	//Removes the value in the Value position of the key
	public void Dequeue(int Value)
	{
		if (Value>Capacity)
		{
			System.out.println("There are not this many items in the key");
		}
		else
		{
			int CInto=into[Value];
			int[] tempInto = new int[Capacity];
			int[] tempOutof = new int[Capacity];
			for (int i=Value; i<=Capacity-1; i++) {
				key[i]=key[i+1];				
			}
			int j=1;
			for (int i=1; i<=Capacity; i++) {

				if(into[i]==Capacity) {
					tempInto[j]=CInto;
					tempOutof[CInto]=j;
					j++;
				}
				else if(into[i]!=CInto) {
					tempInto[j]=into[i];
					tempOutof[into[i]]=j;
					j++;
				}	
			}
			Capacity--;

			Station[] tempKey = new Station[Capacity+1];
			for (int i=1; i<=Capacity; i++) {
				tempKey[i]=key[i];
			}
			key=tempKey;
			into=tempInto;
			outof=tempOutof;
			Heapify(key,outof,into);
		}
	}
	
	//Return smalled value in the heap (the first value)
	public Station Smallest()
	{
		return key[outof[1]];
	}

	public static void main(String[] args)
	{
	int[] testArray = new int[10];
	testArray[1]= 28;
	testArray[2]= 12;
	testArray[3]= 312;
	testArray[4]= 25;
	testArray[5]= 8;
	testArray[6]= 109;
	testArray[7]= 7;
	testArray[8]= 18;
	testArray[9]= 1;
	
	IndirectHeap testHeap = new IndirectHeap(testArray);
	testHeap.Enqueue(5);
	
	testHeap.Dequeue(3);
	System.out.println(Arrays.toString(testHeap.key));
	System.out.println(Arrays.toString(testHeap.into));
	System.out.println(Arrays.toString(testHeap.outof));
	System.out.println(testHeap.Smallest());
	
	
	}
}
