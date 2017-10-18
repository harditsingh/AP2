
public class Set<E extends Comparable> implements SetInterface<E> {
	private ListInterface<E> mainList;
	
	private Set(ListInterface<E> inputList) {
		mainList = inputList;
	}
	
	private List<E> returnList(Set<E> setIn) {
		Set<E> tempSet = new Set<E>(setIn);
		ListInterface<E> listIn = new List<E>();
		while(!tempSet.isEmpty()) {
			listIn.insert(tempSet.retrieve());
			tempSet.remove();
		}
		return (List<E>) listIn;
	}
		
	
	
	public Set() {
		mainList = new List<E>();
	}
	
	public Set(Set<E> oldSet) {
		mainList = new List<E>();
		ListInterface<E> copyList = (ListInterface<E>) oldSet.mainList.copy();

		if(copyList.size() > 0) {
			copyList.goToFirst();

			do {
				this.insert(copyList.retrieve());
			} while(copyList.goToNext());

		}
	}
	
	@Override
	public E retrieve() {
		mainList.goToFirst();
		return mainList.retrieve();
	}
	
	@Override
	public void remove() {
		mainList.remove();
	}
	
	@Override
	public boolean isEmpty() {
		return mainList.isEmpty();
	}

	@Override
	public void insert(E element) {
		if(!mainList.find(element)) {
			mainList.insert(element);
		}
	}
	
	@Override
	public void replaceValue(SetInterface<E> newValue) {
		mainList = returnList((Set<E>) newValue).copy();//return the copy of the list
	}
	
	@Override
	public SetInterface<E> union(SetInterface<E> setIn) {
		ListInterface<E> listIn = returnList((Set<E>) setIn);
		ListInterface<E> tempList = (ListInterface<E>) mainList.copy();

		if(listIn.size() > 0) {
			listIn.goToFirst();

			do {
				if(!tempList.find(listIn.retrieve())) {
					tempList.insert(listIn.retrieve());
				}
			} while(listIn.goToNext());
		}

		return new Set<E>(tempList);
	}

	@Override
	public SetInterface<E> complement(SetInterface<E> setIn) {
		ListInterface<E> listIn = returnList((Set<E>) setIn);
		ListInterface<E> tempList = (ListInterface<E>) mainList.copy();

		if(listIn.size() > 0) {
			listIn.goToFirst();

			do {
				if(tempList.find(listIn.retrieve())) {
					tempList.remove();
				}
			} while(listIn.goToNext());
		}

		return new Set<E>(tempList);
	}

	@Override
	public SetInterface<E> intersection(SetInterface<E> setIn) {
		ListInterface<E> listIn = returnList((Set<E>) setIn);
		ListInterface<E> tempList = (ListInterface<E>) mainList.copy();

		if(mainList.size() > 0) {
			mainList.goToFirst();

			do {
				if(!listIn.find(mainList.retrieve())) {
					tempList.find(mainList.retrieve());
					tempList.remove();
				}
			} while(mainList.goToNext());
		}

		return new Set<E>(tempList);
	}

	@Override
	public SetInterface<E> symmetricDiffence(SetInterface<E> setIn) {
		SetInterface<E> tempSet = union(setIn);
		tempSet = tempSet.complement(intersection(setIn));

		return tempSet;
	}


}
