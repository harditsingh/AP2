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
    private Node currentNode;
    
    List() {
    	size = 0;
    	currentNode = null;
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
    	Node newNode = new Node(d, currentNode, null);
    	if(!this.isEmpty()) {
    		currentNode.next = newNode;
    	}
    	currentNode = newNode;
    	size++;
        return this;
    }

    @Override
    public E retrieve() {
        return currentNode.data;
    }

    @Override
    public ListInterface<E> remove() {
    	currentNode.prior.next = null;
    	currentNode = currentNode.prior;
        return this;
    }

    @Override
    public boolean find(E d) {
        return false;
    }

    @Override
    public boolean goToFirst() {
        return false;
    }

    @Override
    public boolean goToLast() {
        return false;
    }

    @Override
    public boolean goToNext() {
        return false;
    }

    @Override
    public boolean goToPrevious() {
        return false;
    }

    @Override
    public ListInterface<E> copy() {
        return null;
    }
}
