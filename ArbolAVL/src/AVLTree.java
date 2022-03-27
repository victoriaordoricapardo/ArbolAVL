import java.util.ArrayList;

public class AVLTree<T extends Comparable<T>> {
	
    private int cont;
    private NodoAVL<T> root;

    //CONSTRUCTOR DEL ARBOL
    public AVLTree(){
        cont = 0;
        root = null;
    }
    
    
    public AVLTree(T element){
        this();
        root = new NodoAVL<T>(element);
    }
    public AVLTree(NodoAVL<T> root){
        this();
        this.root = root;
    }


    //GET Y SET DE LA RAIZ DEL NODO
    public NodoAVL<T> getRoot() {
        return root;
    }
    public void setRoot(NodoAVL<T> root) {
        this.root = root;
    }
    

    //METODO DE BÚSQUEDA 
    public boolean contains(T element){
        return contains(element, root);
    }
    private boolean contains(T element, NodoAVL<T> current){
        if(current == null)
            return false;
        if(element.compareTo(current.getElement()) < 0)
            return contains(element, current.getLeft());
        if(element.compareTo(current.getElement()) > 0)
            return contains(element, current.getRight());
        return true;
    }

    //METODO PARA CALCULAR LA ALTURA DEL ARBOL
    public int height(NodoAVL<T> current){
        if(current == null)
            return -1;
        else
            return current.getHeight();
    }

    //METODO DE ACTUALIZA DE AUXILIAR DE INDICE DE EQUILIBRIO
    public void updateBalance(NodoAVL<T> current) {
        if (current == null) {
            return;
        }
        if (current.getLeft() != null) {
            updateBalance(current.getLeft());
        }
        if (current.getRight() != null) {
            updateBalance(current.getRight());
        }
        current.setBalance(height(current.getRight()) - height(current.getLeft()));
    }
    
    //METODO PARA DETERMINAR EL INDICE DE EQUILIBRIO FE=BALANCE
    public NodoAVL<T> balance(T element, NodoAVL<T> current) {
    	//SITUACIÓN BASE
        if (current == null) { 
            return current;
        } else {
            if (element.compareTo(current.getElement()) < 0) {
                current.setLeft(balance(element, current.getLeft())); //Recorre
                if (current.getBalance() == -2) {// Aqui debemos rotar pq -2
                    if (current.getRight() == null) {
                        current = rotateLeft(current);
                    } else {
                        current = rotateDoubleLeft(current);
                    }
                }
                if (current.getBalance() == 2) { //Aqui debemos rotar pq 2
                    if (current.getLeft() == null) {
                        current = rotateRight(current);
                    } else {
                        current = rotateDoubleRight(current);
                    }
                }
            } else {
                current.setRight(balance(element, current.getRight()));// Recorre
                if (current.getBalance() == -2) {//  Aqui debemos rotar pq -2
                    if (current.getRight() == null) {
                        current = rotateLeft(current);
                    } else {
                        current = rotateDoubleLeft(current);
                    }
                }
                if (current.getBalance() == 2) {// Aqui debemos rotar pq 2
                    if (current.getLeft() == null) {
                        current = rotateRight(current);
                    } else {
                        current = rotateDoubleRight(current);
                    }
                }
            }
        }
        //Actualizar altura y factor de equilibrio
        current.setHeight(Math.max(height(current.getLeft()), height(current.getRight())) + 1);
        current.setBalance((height(current.getRight()) - height(current.getLeft())));
        return current;
    }

    //METODO PARA INSERTAR recursivo ELEMENTO AL ARBOL
    public boolean insert(T element) {
    	//Caso base que ya exista el elemento
        if (contains(element)) {
            return false;
        } else {
            root = insert(element, root);
            updateBalance(root); //ActualizaFe
            cont++;
            return true;
        }
    }
    
    //Si el elemento afecta equilibrio del arbol se hace una rotación.
    private NodoAVL<T> insert(T element, NodoAVL<T> current) {
        if (current == null) {// Agrega el element
            current = new NodoAVL<T>(element);
            current.setBalance(height(current.getRight()) - height(current.getLeft()));
        } else {
            if (element.compareTo(current.getElement()) < 0) {
                current.setLeft(insert(element, current.getLeft()));// Recorre
                if (height(current.getLeft()) - height(current.getRight()) == 2) {
                    if (element.compareTo(current.getLeft().getElement()) < 0) {
                        current = rotateLeft(current);
                    } else {
                        current = rotateDoubleLeft(current);
                    }
                }
            } else {
                current.setRight(insert(element, current.getRight()));// Recorre
                if (height(current.getRight()) - height(current.getLeft()) == 2) {
                    if (element.compareTo(current.getRight().getElement()) > 0) {
                        current = rotateRight(current);
                    } else {
                        current = rotateDoubleRight(current);
                    }
                }
            }
        }
        //Indicamos su altura y su factor equilibrio
        current.setHeight(Math.max(height(current.getLeft()), height(current.getRight())) + 1);
        current.setBalance(height(current.getRight()) - height(current.getLeft()));
        return current;
    }

    /*METODO PARA ELIMINAR UN ELEMENTO DEL ARBOL CON DIFERENTES CASOS
     * CASO1: CASO ESPECIAL
     * CASO2: CASO SEA LA HOJA
     * CASO3: CASO 1 HIJO
     * CASO4: CASO 2 HIJOS
     */
    public boolean remove(T element) {
        if (!contains(element)) {
            return false;
        }
        NodoAVL<T> current, father = root, temp;
        current = root; // Busca
        while (current.getElement().compareTo(element) != 0) {
            father = current;
            if (element.compareTo(current.getElement()) <= 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        if (current == root && (current.getLeft() == null || current.getRight() == null)) {// caso especial
            if (root.getLeft() != null) {
                root = root.getLeft();
            } else {
                root = root.getRight();
            }
            cont--;
            return true;
        }
        if (current.getLeft() == null && current.getRight() == null) {// caso hoja
            if (current.getElement().compareTo(father.getElement()) < 0) {
                father.setLeft(null);
            } else {
                father.setRight(null);
            }
        } else {
            if (!(current.getLeft() != null && current.getRight() != null)) {// un hijo
                father.hang(current.getLeft());
                father.hang(current.getRight());
            } else {// buscar suc-in order, 2 hijos
                temp = current;
                current = current.getRight();
                father = current;
                while (current.getLeft() != null) {
                    father = current;
                    current = current.getLeft();
                }
                temp.setElement(current.getElement());
                if (father == current) {
                    temp.setRight(current.getRight());
                } else {
                    father.setLeft(current.getRight());
                }
            }
            cont--;
        }

        updateBalance(root);
        balance(element, root);
        updateBalance(root);
        return true;
    }


    //METODO ROTACION IZQUIERDA
    public NodoAVL<T> rotateLeft(NodoAVL<T> current) {
    	NodoAVL<T> temp = current.getLeft(); //Auxiliar guadar
        current.setLeft(temp.getRight());
        temp.setRight(current);
        //Actualizamos altura y fe
        current.setHeight(Math.max(height(current.getLeft()), height(current.getRight())) + 1);
        temp.setHeight(Math.max(height(temp.getLeft()), current.getHeight()) + 1);
        return temp;
    }
    
    //METODO ROTACION IZQUIERDA IZQUIERDA
    public NodoAVL<T> rotateDoubleLeft(NodoAVL<T> current) {
    	//Usamos metodo rotacion derecha y rotacion izquierda
        current.setLeft(rotateRight(current.getLeft()));
        return rotateLeft(current);
    }

    //METODO ROTACION DERECHA
    public NodoAVL<T> rotateRight(NodoAVL<T> current) {
    	NodoAVL<T> temp = current.getRight(); //auxiliar guardar
        current.setRight(temp.getLeft());
        temp.setLeft(current);
        //actualizams altura y fe
        current.setHeight(Math.max(height(current.getLeft()), height(current.getRight())) + 1);
        temp.setHeight(Math.max(height(temp.getRight()), current.getHeight() + 1));
        return (temp);
    }
    
    //METODO ROTACION DERECHA DERECHA 
    //Usamos metodo rotacion izq y rotacion derecha
    public NodoAVL<T> rotateDoubleRight(NodoAVL<T> current) {
        current.setRight(rotateLeft(current.getRight()));
        return rotateRight(current);
    }


    /*METODO IMPRIME DE IZQ A DERECHA POR NIVELES
     * IMPRIME ELEMENTO 
     * IMPRIME INDICE DE EQUILIBRIO
     */
    
    public String imprime() {
        StringBuilder sb = new StringBuilder(); //Auxiliar 
        ArrayList<NodoAVL<T>> aux = new ArrayList<>(); //Auxiliar
        ArrayList<T> lista1 = new ArrayList<>(); //Auxiliar
        ArrayList<Integer> lista2 = new ArrayList<>(); //Auxiliar
        
        aux.add(root);
        NodoAVL<T> temp;
        //Agregamos elemntos de arbol a lista1
        //Agregamos factor equilibrio a lista2
        //Acabmos cuando está vacio
        while (!aux.isEmpty()) {
            temp = aux.remove(0);
            lista1.add(temp.getElement());
            lista2.add(temp.getBalance());
            if (temp.getLeft() != null) {
                aux.add(temp.getLeft());
            }
            if (temp.getRight() != null) {
                aux.add(temp.getRight());
            }
        }
        //Agregamos valores de listas a StringBuilder
        while (lista1.iterator().hasNext()) {
            sb.append("Elemento: " + lista1.remove(0) + "\n");
            sb.append("        Fe: " + lista2.remove(0) + "\n\n");
        }

        return sb.toString();
    }
}
