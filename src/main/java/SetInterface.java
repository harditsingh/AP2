/**	@elements : objects of type E
 *	@structure : linear
 *	@domain : 	The elements in the Set are sorted monotonically increasing.
 *				All rows of elements of type E are valid values for a list.
 *				Each element having a particular value is present exactly once in the Set
 *	@constructor - Set(): Normal Constructor
 *				   Set(Set<E>): Copy Constructor
 *				   
 *	<dl>
 *		<dt><b>PRE-condition</b><dd>		-
 *		<dt><b>POST-condition</b><dd> 	The new SetInterface object an empty set.
 * </dl>
 **/


public interface SetInterface<E> {

	/** @precondition  - The set is not empty.
     *	@postcondition - The value of the first element in the has been returned.
     */
    E retrieve();
	
    
    /** @precondition  - The set is not empty.
     *	@postcondition - The most recently retrieved element is removed from the set.
     */
    void remove();
    
    
    /** @precondition  - 
     *	@postcondition - TRUE: The set does not contain any elements
     *					 FALSE: The set contains at least one element
     */
    boolean isEmpty();
    
    /** @precondition  -
     *	@postcondition - Element 'element' has been added to Set-PRE.
     **/
    void insert(E element);
    
    /** @precondition  -
     *	@postcondition - The elements of the Set 'newValue' are copied to this Set
     **/
    void replaceValue(SetInterface<E> newValue);
    
    /** @precondition  - 
     *	@postcondition - A set containing the result of union of Set 'setIn' and this Set is returned
     **/
    SetInterface<E> union(SetInterface<E> setIn);
    
    /** @precondition  - 
     *	@postcondition - A set containing the result of complement of Set 'setIn' and this Set is returned
     **/
    SetInterface<E> complement(SetInterface<E> setIn);
    
    /** @precondition  - 
     *	@postcondition - A set containing the result of intersection of Set 'setIn' and this Set is returned
     **/
    SetInterface<E> intersection(SetInterface<E> setIn);
    
    /** @precondition  - 
     *	@postcondition - A set containing the result of symmetric-difference of Set 'setIn' and this Set is returned
     **/
    SetInterface<E> symmetricDiffence(SetInterface<E> setIn);

}
