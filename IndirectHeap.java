/**
 * Test IndirectHeap
 * 
 * @author Ashwin Deen Masi
 * @studentID 3163458
 * @lastModified: 25-10-2018
 */
import java.io.File;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;
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
		//if heap is empty
		if (key[1]==null) {
			key[1]		= Value;
			into[1]		= 1;
			outof[1] 	= 1;
		}
		else {
			Capacity++;
			Station[] tempKey = new Station[Capacity+1];
			int[] tempInto = new int[Capacity+1];
			int[] tempOutof = new int[Capacity+1];
			//copy all values into new arrays
			for (int i=1; i<=Capacity-1; i++) {
				tempKey[i] = key[i];
				tempInto[i] = into[i];
				tempOutof[i] = outof[i]; 
			}
			//place newest value into key and place it ad the end of the heap
			tempKey[Capacity] = Value;
			tempInto[Capacity] = Capacity;
			tempOutof[Capacity] = Capacity;
			key = tempKey;
			into = tempInto;
			outof = tempOutof;
			//reorder heap
			Heapify(key,outof,into);
		}
	}
	//Removes first value in heap
	public void Dequeue()
	{
		//If heap is empty
		if (key[1]==null) {
			System.out.println("Heap is empty");
		}
		//If heap has only 1 value
		else if (Capacity==1)
		{
			key[1]=null;
		}
		else 
		{
			Station[] tempKey = new Station[Capacity];
			int[] tempInto = new int[Capacity];
			int[] tempOutof = new int[Capacity];
			int j=1;
			//copy all values in key except one in first place
			for (int i=1; i<=Capacity; i++) {
				if(i!=outof[1]) {
				
					tempKey[j]=key[i];
					j++;
				}
			}
			key = tempKey;
			j=1;
			//Place value in last place in first. Copy all values apart from 1 
			//update outof each time we change into
			for (int i=1; i<=Capacity; i++) {
				if(into[i]==Capacity)
				{
					tempInto[j]=1;
					tempOutof[1]=j;
					j++;
				}
				else if (into[i]!=1)
				{
					tempInto[j]=into[i];
					tempOutof[into[i]]=j;
					j++;
				}
			}
			into = tempInto;
			outof = tempOutof;
			Capacity--;
		}
		
		//reorder heap
		Heapify(key,outof,into);
	}

	//Return true if the Heap is empty
	public boolean isEmpty()
	{
		if (key[1]==null)
			return true;
		else
			return false;
	}
	
	//Return smalled value in the heap (the first value)
	public Station Smallest()
	{
		return key[outof[1]];
	}

	
	public static void main(String[] args)
	{

		Station station1 = new Station("Name1", "Line1", true);
		station1.setTime(50);
		station1.getTime(); // Returns 50
		station1.setChanges(50);
		station1.getTime(); // Returns 50

		Station station2 = new Station("Name2", "Line2", true);
		station2.setTime(25);
		station2.getTime(); // Returns 50
		station2.setChanges(25);
		station2.getTime(); // Returns 50

		Station station3 = new Station("Name3", "Line3", true);
		station3.setTime(20);
		station3.getTime(); // Returns 50
		station3.setChanges(20);
		station3.getTime(); // Returns 50

		Station station4 = new Station("Name4", "Line4", true);
		station4.setTime(5);
		station4.getTime(); // Returns 50
		station4.setChanges(5);
		station4.getTime(); // Returns 50

		Station station5 = new Station("Name5", "Line5", true);
		station5.setTime(10);
		station5.getTime(); // Returns 50
		station5.setChanges(10);
		station5.getTime(); // Returns 50

		Station station6 = new Station("Name6", "Line6", true);
		station6.setTime(35);
		station6.getTime(); // Returns 50
		station6.setChanges(35);
		station6.getTime(); // Returns 50

		Station station7 = new Station("Name7", "Line7", true);
		station7.setTime(1);
		station7.getTime(); // Returns 50
		station7.setChanges(1);
		station7.getTime(); // Returns 50

		IndirectHeap Heap = new IndirectHeap();
		Heap.Enqueue(station1);
		System.out.println(Heap.isEmpty());
		System.out.println(Heap.Capacity);
		System.out.println(Arrays.toString(Heap.key));
		System.out.println(Arrays.toString(Heap.into));
		System.out.println(Arrays.toString(Heap.outof));
		Heap.Enqueue(station2);
		System.out.println(Heap.isEmpty());
		System.out.println(Heap.Capacity);
		System.out.println(Arrays.toString(Heap.key));
		System.out.println(Arrays.toString(Heap.into));
		System.out.println(Arrays.toString(Heap.outof));
		Heap.Enqueue(station3);
		System.out.println(Heap.isEmpty());
		System.out.println(Heap.Capacity);
		System.out.println(Arrays.toString(Heap.key));
		System.out.println(Arrays.toString(Heap.into));
		System.out.println(Arrays.toString(Heap.outof));
		Heap.Enqueue(station4);
		System.out.println(Heap.isEmpty());
		System.out.println(Heap.Capacity);
		System.out.println(Arrays.toString(Heap.key));
		System.out.println(Arrays.toString(Heap.into));
		System.out.println(Arrays.toString(Heap.outof));
		Heap.Enqueue(station5);
		System.out.println(Heap.isEmpty());
		System.out.println(Heap.Capacity);
		System.out.println(Arrays.toString(Heap.key));
		System.out.println(Arrays.toString(Heap.into));
		System.out.println(Arrays.toString(Heap.outof));
		Heap.Enqueue(station6);
		System.out.println(Heap.isEmpty());
		System.out.println(Heap.Capacity);
		System.out.println(Arrays.toString(Heap.key));
		System.out.println(Arrays.toString(Heap.into));
		System.out.println(Arrays.toString(Heap.outof));
		Heap.Enqueue(station7);
		System.out.println(Heap.isEmpty());
		System.out.println(Heap.Capacity);
		System.out.println(Arrays.toString(Heap.key));
		System.out.println(Arrays.toString(Heap.into));
		System.out.println(Arrays.toString(Heap.outof));
		
	
		Heap.Dequeue();
		System.out.println(Heap.isEmpty());
		System.out.println(Heap.Capacity);
		System.out.println(Arrays.toString(Heap.key));
		System.out.println(Arrays.toString(Heap.into));
		System.out.println(Arrays.toString(Heap.outof));
		Heap.Dequeue();
		System.out.println(Heap.isEmpty());
		System.out.println(Heap.Capacity);
		System.out.println(Arrays.toString(Heap.key));
		System.out.println(Arrays.toString(Heap.into));
		System.out.println(Arrays.toString(Heap.outof));
		Heap.Dequeue();
		System.out.println(Heap.isEmpty());
		System.out.println(Heap.Capacity);
		System.out.println(Arrays.toString(Heap.key));
		System.out.println(Arrays.toString(Heap.into));
		System.out.println(Arrays.toString(Heap.outof));
		Heap.Dequeue();
		System.out.println(Heap.isEmpty());
		System.out.println(Heap.Capacity);
		System.out.println(Arrays.toString(Heap.key));
		System.out.println(Arrays.toString(Heap.into));
		System.out.println(Arrays.toString(Heap.outof));
		Heap.Dequeue();
		System.out.println(Heap.isEmpty());
		System.out.println(Heap.Capacity);
		System.out.println(Arrays.toString(Heap.key));
		System.out.println(Arrays.toString(Heap.into));
		System.out.println(Arrays.toString(Heap.outof));
		Heap.Dequeue();
		System.out.println(Heap.isEmpty());
		System.out.println(Heap.Capacity);
		System.out.println(Arrays.toString(Heap.key));
		System.out.println(Arrays.toString(Heap.into));
		System.out.println(Arrays.toString(Heap.outof));
		Heap.Dequeue();
		System.out.println(Heap.isEmpty());
		System.out.println(Heap.Capacity);
		System.out.println(Arrays.toString(Heap.key));
		System.out.println(Arrays.toString(Heap.into));
		System.out.println(Arrays.toString(Heap.outof));
		Heap.Dequeue();
		System.out.println(Heap.isEmpty());
		System.out.println(Heap.Capacity);
		System.out.println(Arrays.toString(Heap.key));
		System.out.println(Arrays.toString(Heap.into));
		System.out.println(Arrays.toString(Heap.outof));
		
		
	
	}
}
