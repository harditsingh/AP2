public class List<E extends Comparable> implements ListInterface<E>{

	private class Node {

		E data;
		Node prior,
		next;

		public Node(E d) {
			this(d, null, null);
		}

		public Node(E data, Node prior, Node next) {
			this.data = data == null ? null : data;
			this.prior = prior;
			this.next = next;
		}

	}

	private int size;
	private Node lastNode;
	private Node firstNode;
	private Node currentNode;

	List() {
		size = 0;
		lastNode = null;
	}

	@Override
	public boolean isEmpty() {
		boolean status = (size == 0) ? true : false;
		return status;
	}

	@Override
	public ListInterface<E> init() {
		size = 0;
		return this;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public ListInterface<E> insert(E d) {
		
		if(this.isEmpty()) {
			Node newNode = new Node(d, null, null);
			lastNode = newNode;
			firstNode = newNode;
			currentNode = newNode;
		}
		else if(firstNode.data.compareTo(d) >= 0) {
			Node newNode = new Node(d, null, currentNode);
			firstNode.prior = newNode;
			firstNode = newNode;
			currentNode = firstNode;
		}
		else if(firstNode.data.compareTo(d) < 0 && lastNode.data.compareTo(d) > 0) {
			this.find(d);
			Node newNode = new Node(d, currentNode.prior, currentNode);
			currentNode.prior.next = newNode;
			currentNode.prior = newNode;
			currentNode = newNode;
		}
		else {
			Node newNode = new Node(d, currentNode, null);
			lastNode.next = newNode;
			lastNode = newNode;
			currentNode = lastNode;
		}

		size++;
		return this;
	}

	@Override
	public E retrieve() {
		return currentNode.data;
	}

	@Override
	public ListInterface<E> remove() {
		if(this.size() == 1) {
			currentNode = firstNode = lastNode = null;
		}
		else {
			if(currentNode.equals(firstNode)) {
				currentNode.next.next = currentNode.next;
				currentNode = currentNode.next;
			}
			else if(currentNode.equals(lastNode)) {
				currentNode.prior.next = currentNode.next;
				currentNode = currentNode.prior;
			}
			else {
				currentNode.prior.next = currentNode.next;
				currentNode = currentNode.next;
			}
		}
		size--;
		return this;
	}

	@Override
	public boolean find(E d) {
		if(this.isEmpty()) {
			return false;
		}

		this.goToFirst();
		if(this.retrieve().compareTo(d) > 0) {
			return false;
		}

		do {
			if(this.retrieve().compareTo(d) == 0) {
				return true;
			}
			else if(this.retrieve().compareTo(d) > 0) {
				return false;
			}

		} while(this.goToNext());

		return false;
	}

	@Override
	public boolean goToFirst() {
		if(!this.isEmpty()) {
			currentNode = firstNode;
			return true;
		}

		return false;
	}

	@Override
	public boolean goToLast() {
		if(!this.isEmpty()) {
			currentNode = lastNode;
			return true;
		}

		return false;
	}

	@Override
	public boolean goToNext() {
		if(!this.isEmpty() && !currentNode.equals(lastNode)) {
			currentNode = currentNode.next;
			return true;
		}

		return false;
	}

	@Override
	public boolean goToPrevious() {
		if(!this.isEmpty() && !currentNode.equals(firstNode)) {
			currentNode = currentNode.prior;
			return true;
		}

		return false;
	}

	@Override
	public ListInterface<E> copy() {
		ListInterface<E> newList = new List<E>();
		newList.init();

		this.goToFirst();

		while(this.goToNext()) {
			newList.insert(this.retrieve());
		}

		return newList;
	}
}
