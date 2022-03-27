
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AVLTree<Integer> avltree = new AVLTree<Integer>();
		
		avltree.insert(30);
		avltree.insert(2);
		avltree.insert(0);
		avltree.insert(100);
		avltree.insert(90);
		avltree.insert(40);
		avltree.insert(-100);
		avltree.insert(20);
		avltree.insert(1);
		avltree.insert(-110);
		avltree.insert(10);

		System.out.println("ARBOL ORIGINAL\n");
		System.out.println(avltree.imprime());

		
		//Elimina elemento 
		System.out.println("ARBOL ELEMENTOS ELIMINADOS \n");
		
		avltree.remove(2);
		  
		System.out.println(avltree.imprime());
		  
		
		
	}

}
