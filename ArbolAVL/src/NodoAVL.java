public class NodoAVL<T extends Comparable<T>>{
    private T element;
    private NodoAVL<T> left;
    private NodoAVL<T> right;
    private NodoAVL<T> father;
    private int balance;
    private int height;

    public NodoAVL(){
        left = null;
        right = null;
    }
    public NodoAVL(T element) {
        this();
        this.element = element;
        balance = 0;
        height = 0;
    }

    public void hang(NodoAVL<T> current){
        if (current == null) {
            return;
        }
        if (current.getElement().compareTo(this.getElement()) <= 0) {
            setLeft(current);
        } else {
            setRight(current);
        }
        current.setFather(this);
    }

    public T getElement() {
        return element;
    }
    public void setElement(T element) {
        this.element = element;
    }
    public NodoAVL<T> getLeft() {
        return left;
    }
    public void setLeft(NodoAVL<T> left) {
        this.left = left;
    }
    public NodoAVL<T> getRight() {
        return right;
    }
    public void setRight(NodoAVL<T> right) {
        this.right = right;
    }
    public NodoAVL<T> getFather() {
        return father;
    }
    public void setFather(NodoAVL<T> father) {
        this.father = father;
    }
    public int getBalance() {
        return balance;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
}
