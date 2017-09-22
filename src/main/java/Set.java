
public class Set<E extends Comparable> implements SetInterface<E> {
	private List<E> mainList;



	public Set(E[] setData) {
		mainList = new List<E>();

		for(E element : setData) {
			mainList.insert(element);
		}
	}

	private List<E> returnList() {
		return mainList;
	}

	public void add(Set<E> newSet) {
		List<E> newList = newSet.returnList();

		newList.goToFirst();

		do {
			if(!mainList.find(newList.retrieve())) {
				mainList.insert(newList.retrieve());
			}
		} while(newList.goToNext());
	}

	public void subtract(Set<E> newSet) {
		List<E> newList = newSet.returnList();

		newList.goToFirst();

		do {
			if(mainList.find(newList.retrieve())) {
				mainList.remove();
			}
		} while(newList.goToNext());
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
