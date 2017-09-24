
public class Set<E extends Comparable> implements SetInterface<E> {
	private List<E> mainList;

	public Set() {
		mainList = new List<E>();
	}
	
	public Set(E[] setData) {
		mainList = new List<E>();

		for(E element : setData) {
			mainList.insert(element);
		}
	}
	
	private Set(List<E> inputList) {
		mainList = inputList;
	}

	public List<E> returnList() {
		return mainList;
	}
	
	public void insert(E element) {
		mainList.insert(element);
	}
	
	public void replaceValue(Set newValue) {
		mainList = newValue.returnList();
		
	}

	public Set<E> union(Set<E> setIn) {
		List<E> listIn = setIn.returnList();
		List<E> tempList = (List<E>) mainList.copy();
		
		listIn.goToFirst();

		do {
			if(!tempList.find(listIn.retrieve())) {
				tempList.insert(listIn.retrieve());
			}
		} while(listIn.goToNext());
		
		return new Set<E>(tempList);
	}

	public Set<E> complement(Set<E> setIn) {
		List<E> listIn = setIn.returnList();
		List<E> tempList = (List<E>) mainList.copy();
		
		listIn.goToFirst();

		do {
			if(tempList.find(listIn.retrieve())) {
				tempList.remove();
			}
		} while(listIn.goToNext());
		
		return new Set<E>(tempList);
	}
	
	public Set<E> intersection(Set<E> setIn) {
		List<E> listIn = setIn.returnList();
		List<E> tempList = (List<E>) mainList.copy();
		
		mainList.goToFirst();
		
		do {
			if(!listIn.find(mainList.retrieve())) {
				tempList.find(mainList.retrieve());
				tempList.remove();
			}
		} while(mainList.goToNext());
		
		return new Set<E>(tempList);
	}
	
	
	public Set<E> symmetricDiffence(Set<E> setIn) {
		Set<E> tempSet = union(setIn);
		tempSet = tempSet.complement(intersection(setIn));
		
		return tempSet;
	}


	public void printSet() {
		mainList.goToFirst();

		System.out.print("{");

		if(!mainList.isEmpty()) {
			do {
				System.out.print(mainList.retrieve());
				if(mainList.goToNext()) {
					System.out.print(", ");
				}
				else {
					break;
				}
			} while(true);
		}
		System.out.println("}");
	}






}
