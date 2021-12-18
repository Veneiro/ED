package mainHT;

public class HashNode<T> {

	public static final Integer EMPTY = 0;
	public static final Integer VALID = 1;
	public static final Integer DELETED = 2;

	private Integer status = EMPTY;
	private T element;

	/**
	 * @return the status
	 */
	protected Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	protected void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the element
	 */
	protected T getElement() {
		return element;
	}

	/**
	 * @param element the element to set
	 */
	protected void setElement(T element) {
		this.element = element;
	}
	
}
