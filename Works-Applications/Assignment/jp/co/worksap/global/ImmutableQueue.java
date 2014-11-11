package jp.co.worksap.global;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * The Queue class represents an immutable first-in-first-out (FIFO) queue of objects. 
 * To implement such an immutable queue such that push and pop operations are O(1) 
 * we maintain two stacks stack A and B, A is used to enqueue element and B is used to dequeue
 * element. Newly entered element is always at the top of stack A. Enqueue operation just pushes the new element on A.
 * When B is empty, we simply pop all elements of A and push them on B. If A is also empty, then  it's an error.
 * Stack A and B are also immutable stacks.
 *
 * @param <E>
 */
public class ImmutableQueue<E> {
	/**
	 * Immutable Stack
	 * @param <E>
	 */
	private static class ImmutableStack<E> {
	
		/**
		* head - the top object element element
		* tail - rest of the stack as an ImmutableStack Object
		* size - size of the stack
		*/
		private E head;
		private ImmutableStack<E> tail;
		private int size;

		/**
		* Constructor for ImmutableStack.
		*/
		private ImmutableStack(E obj, ImmutableStack<E> tail) {
			this.head = obj;
			this.tail = tail;
			this.size = tail.size + 1;
		}
		
		/**
		* Method to return an empty ImmutableStack object.
		* @return ImmutableStack
		*/
		public static ImmutableStack emptyStack() {
			return new ImmutableStack();
		}
		
		/**
		* Constructor to generate empty ImmutableStack object.
		*/
		private ImmutableStack() {
			this.head = null;
			this.tail = null;
			this.size = 0;
		}

		/**
		 * Get a new stack reverse of the current stack.
		 * 
		 * @return
		 */
		public ImmutableStack<E> toReverseStack() {
			ImmutableStack<E> stack = new ImmutableStack<E>();
			ImmutableStack<E> body = this;
			while (!body.isEmpty()) {
				stack = stack.push(body.head);
				body = body.tail;
			}
			return stack;
		}

		public boolean isEmpty() {
			return this.size == 0;
		}

		public ImmutableStack<E> push(E obj) {
			return new ImmutableStack<E>(obj, this);
		}
		
		public String toString(){
			ImmutableStack<E> body = this;
			String S = new String();
			if(body.isEmpty())
				return S;
			S = body.head.toString();
			body = body.tail;
			while (!body.isEmpty()) {
				S = S + ", " + body.head.toString();
				body = body.tail;
			}
			return S;
		}
	}

	private ImmutableStack<E> A;
	/**
	 * The reverse stack of A
	 */
	private ImmutableStack<E> B;

	
	private ImmutableQueue(ImmutableStack<E> A, ImmutableStack<E> B) {
		this.A = A;
		this.B = B;
	}
	
	/**
	 * requires default constructor. Return empty queue
	 * @return ImmutableQueue
	 */
	public ImmutableQueue() {
		this.A = ImmutableStack.emptyStack();
		this.B = ImmutableStack.emptyStack();
	}

	/**
	 * Returns the queue that adds an item into the tail of this queue without
	 * modifying this queue.
	 * 
	 * <pre>
	 * e.g.
	 *  When this queue represents the queue (2,1,2,2,6) and we enqueue the value 4 into this queue,
	 *  this method returns a new queue (2,1,2,2,6,4)
	 *  and this object still represents the queue (2,1,2,2,6)
	 * </pre>
	 * 
	 * @param e
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ImmutableQueue<E> enqueue(E e) {
		if (e == null)
			throw new IllegalArgumentException();
		return new ImmutableQueue<E>(this.A.push(e), this.B);
	}

	/**
	 * Returns the queue that removes the object at the head of this queue
	 * without modifying this queue.
	 * 
	 * <pre>
	 * e.g.
	 *  When this queue represents the queue (7,1,3,3,5,1) .
	 *  this method returns a new queue (1,3,3,5,1)
	 *  and this object still represents the queue (7,1,3,3,5,1)
	 * </pre>
	 * 
	 * If this queue is empty, throws java.util.NoSuchElementException.
	 * 
	 * @param e
	 * @return
	 */
	public ImmutableQueue<E> dequeue() {
		if (this.isEmpty())
			throw new NoSuchElementException();
		if (!this.B.isEmpty()) {
			return new ImmutableQueue<E>(this.A, this.B.tail);
		} else {
			// revers the ordered stack then "clean" that stack
			return new ImmutableQueue<E>(ImmutableStack.emptyStack(),
				this.A.toReverseStack().tail);
		}
	}

	/**
	 * This method simply reverse the order stack and assign it to reverse
	 * stack, the internal queue is not modified, it is necessary since all new
	 * objects are enqueued to order stack, while peek() looking at the reverse
	 * stack, we do not want to reverse the order stack again and again while
	 * looking for the head of reverse when it is empty.
	 */
	private void balanceQueue() {
		this.B = this.A.toReverseStack();
		this.A = ImmutableStack.emptyStack();
	}

	/**
	 * Looks at the object which is the head of this queue without removing it
	 * from the queue. 
	 * Head is the top (head) of reverse order stack
	 * <pre>
	 * e.g.
	 *  When this queue represents the queue (7,1,3,3,5,1) .
	 *  this method returns 7 and this object still represents the queue (7,1,3,3,5,1)
	 * </pre>
	 *
	 * @param e
	 * @return
	 * @throws NoSuchElementException
	 */
	public E peek() {
		if (this.isEmpty())
				throw new NoSuchElementException();
		if (this.B.isEmpty())
				balanceQueue();
		return this.B.head;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the number of objects in this queue.
	 * 
	 * @return
	 */
	public int size() {
		return this.A.size + this.B.size;
	}
	
	public String toString() {
		String S = new String();
		S = S + this.B.toString();
		S = S + this.A.toReverseStack().toString();
		return S;
	}
	 public static void main(String[] args) { 
		//To test the class functions
		ImmutableQueue<String> a = new ImmutableQueue<String>();
		String S1 = "E1";
		ImmutableQueue<String> b = a.enqueue(S1);
		System.out.println(a.toString());
		System.out.println(b.toString());
		ImmutableQueue<String> c = b.enqueue("E2");
		System.out.println(c.toString());
		ImmutableQueue<String> d = c.dequeue();
		System.out.println(d.toString());
	}
}
