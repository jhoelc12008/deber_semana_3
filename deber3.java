
import java.util.Scanner;

class Producto {
    int id;
    String nombre;

    Producto(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}

class NodoAlerta {
    Producto producto;
    double total;
    NodoAlerta siguiente;

    NodoAlerta(Producto producto, double total) {
        this.producto = producto;
        this.total = total;
        this.siguiente = null;
    }
}

class NodoArbol {
    int id;
    double total;
    NodoArbol izquierdo;
    NodoArbol derecho;

    NodoArbol(int id, double total) {
        this.id = id;
        this.total = total;
        this.izquierdo = null;
        this.derecho = null;
    }
}

public class deber3 {

    static NodoArbol insertarArbol(NodoArbol raiz, int id, double total) {
        if (raiz == null) {
            return new NodoArbol(id, total);
        }

        if (total < raiz.total || (total == raiz.total && id < raiz.id)) {
            raiz.izquierdo = insertarArbol(raiz.izquierdo, id, total);
        } else {
            raiz.derecho = insertarArbol(raiz.derecho, id, total);
        }

        return raiz;
    }

    static void recorrerInorden(NodoArbol raiz) {
        if (raiz != null) {
            recorrerInorden(raiz.izquierdo);
            System.out.println("Código: " + raiz.id + " | Total: " + raiz.total);
            recorrerInorden(raiz.derecho);
        }
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        double minimo = 500;

        System.out.print("Cantidad de productos: ");
        int n = entrada.nextInt();
        entrada.nextLine();

        if (n <= 0) {
            System.out.println("Debe ingresar al menos un producto.");
            entrada.close();
            return;
        }

        Producto[] productos = new Producto[n];
        double[][] cantidades = new double[n][5];
        double[] totales = new double[n];
        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};

        NodoAlerta cabeza = null;
        NodoArbol raiz = null;

        for (int i = 0; i < n; i++) {
            System.out.println("\nProducto " + (i + 1));

            System.out.print("Código: ");
            int id = entrada.nextInt();
            entrada.nextLine();

            System.out.print("Nombre: ");
            String nombre = entrada.nextLine();

            productos[i] = new Producto(id, nombre);

            for (int j = 0; j < 5; j++) {
                System.out.print(dias[j] + ": ");
                cantidades[i][j] = entrada.nextDouble();
                totales[i] = totales[i] + cantidades[i][j];
            }
        }

        System.out.println("\n=== TOTALES SEMANALES ===");

        for (int i = 0; i < n; i++) {
            System.out.println(productos[i].nombre + " (" + productos[i].id + "): " + totales[i]);

            if (totales[i] < minimo) {
                NodoAlerta nuevo = new NodoAlerta(productos[i], totales[i]);
                nuevo.siguiente = cabeza;
                cabeza = nuevo;
            }

            raiz = insertarArbol(raiz, productos[i].id, totales[i]);
        }

        System.out.println("\n=== ALERTAS DE REPOSICIÓN (MENOR A 500) ===");

        NodoAlerta actual = cabeza;

        if (actual == null) {
            System.out.println("No existen alertas.");
        }

        while (actual != null) {
            System.out.println(actual.producto.nombre + " | Total: " + actual.total);
            actual = actual.siguiente;
        }

        System.out.println("\n=== ÁRBOL EN INORDEN (MENOR A MAYOR) ===");
        recorrerInorden(raiz);

        entrada.close();
    }
}

