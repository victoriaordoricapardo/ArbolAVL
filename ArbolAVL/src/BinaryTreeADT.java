import java.util.Iterator;

public interface BinaryTreeADT<T>{
	
  public boolean isEmpty();
  public int size();
  public boolean contains( T elem);
  public Iterator<T> iteratorPreOrden();
  public Iterator<T> iteratorPostOrden();
  public Iterator<T> iteratorInOrden();


}