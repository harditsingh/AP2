
public class Set<E extends Comparable> implements SetInterface<E> {
	private List<E> mainList;
	//Don't add duplicate objects
	
	private Set(List<E> inputList) { // Won't remove, this is for internal usage!
		mainList = inputList;
	}
		
	public Set() {
		mainList = new List<E>();
	}
	
	public Set(Set<E> oldSet) {
		mainList = new List<E>();
		List<E> copyList = (List<E>) oldSet.mainList.copy();

		if(copyList.size() > 0) {
			copyList.goToFirst();

			do {
				this.insert(copyList.retrieve());
			} while(copyList.goToNext());

		}
	}
	
	public E retrieve() {
		mainList.goToFirst();
		return mainList.retrieve();
	}
	
	public void remove() {
		mainList.remove();
	}
	
	public boolean isEmpty() {
		return mainList.isEmpty();
	}

	public void insert(E element) {
		if(!mainList.find(element)) {
			mainList.insert(element);
		}
	}

	public void replaceValue(Set newValue) {
		mainList = (List<E>) newValue.mainList.copy();//return the copy of the list
	}

	public Set<E> union(Set<E> setIn) {
		List<E> listIn = setIn.mainList;
		List<E> tempList = (List<E>) mainList.copy();

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

	public Set<E> complement(Set<E> setIn) {
		List<E> listIn = setIn.mainList;
		List<E> tempList = (List<E>) mainList.copy();

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

	public Set<E> intersection(Set<E> setIn) {
		List<E> listIn = setIn.mainList;
		List<E> tempList = (List<E>) mainList.copy();

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


	public Set<E> symmetricDiffence(Set<E> setIn) {
		Set<E> tempSet = union(setIn);
		tempSet = tempSet.complement(intersection(setIn));

		return tempSet;
	}

}
