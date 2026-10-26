# TP1 — Équation du second degré, exceptions, IHM Swing, tracé de courbe

Projet Java simple, sans bibliothèque externe.
Import : `File > Import... > General > Existing Projects into Workspace` →
`/home/cornic/eclipsebis/TP1_complet`.

## Correspondance sujet → classes

| Question du sujet | Classe à lancer | Contenu |
|---|---|---|
| **1.1** Hello World | `HelloWorld` | une classe, une méthode `Aff()` et un `main()` |
| **2.1** Saisie au clavier | `Tp1` | `Scanner`, tests (`if`) contre `a = 0` et les saisies non numériques |
| **2.2** Saisie en boîtes de dialogue | `Tp1Dialogue` | `JOptionPane.showInputDialog` **et** `showOptionDialog` (les 3 coefficients dans une seule boîte, figure 1) |
| **3** Exceptions | `Tp1Exceptions` | `BufferedReader`, blocs `try`/`catch`, et deux exceptions maison : `SaisieInvalideException`, `CoefficientNulException` |
| **4** Hors IDE, en console | `lancer_console.sh` / `.bat` | compile avec `javac` puis exécute avec `java`, sans Eclipse |
| **5** IHM Swing + WindowBuilder | `FenetrePrincipale` | `JFrame`, barre de menu en `JMenuItem`, panneau graphique, 2 `JLabel` de racines, boîte `BoiteSaisie` modale |
| **6** Boîte non modale | `FenetrePrincipale` | option de menu « Saisie en boite non modale » : la boîte reste ouverte et dialogue avec la fenêtre (`EcouteurCoefficients`) |
| **7** Tracé de la courbe | `MonPanel` | `paintComponent`, `DessineGraph(Graphics2D, Dimension, Color)`, `Ajuste()`, et les données membres imposées |

La classe de calcul `ResolEq` est commune à toutes les versions : comme le
demande le sujet, elle n'est **jamais modifiée** quand on passe de la console
aux boîtes de dialogue puis à l'IHM.

## Pour voir l'essentiel en une fois

Lancer `FenetrePrincipale` → menu **Programme** :

1. **Lancer le programme** → saisir a, b, c (par défaut 1, -3, 2) → les racines
   s'affichent dans les deux `JLabel` ;
2. **Affichage en mode dessin** → la parabole est tracée et les racines réelles
   sont marquées en rouge ;
3. **Aide** et **Quitter** → les deux autres gestionnaires d'événement demandés.

## Remarques

- `Ajuste()` réinitialise les bornes avec `-Double.MAX_VALUE` pour les maxima :
  `Double.MIN_VALUE`, donné dans le sujet, est le plus petit double **positif**
  et fausserait le calcul dès qu'une ordonnée est négative.
- Les deux `JLabel` sont posés dans un bandeau transparent de `MonPanel`, pour
  qu'ils restent lisibles par-dessus le dessin.
- `FenetrePrincipale`, `BoiteSaisie` et `MonPanel` sont écrites dans le style de
  WindowBuilder : `Open With > WindowBuilder Editor` fonctionne, l'onglet
  *Design* permet de continuer à la souris.

## Variante Maven (vérifier que m2e fonctionne)

Le projet contient aussi un `pom.xml`. Il déclare `<sourceDirectory>src</sourceDirectory>`
pour garder l'arborescence Eclipse telle quelle : **rien n'est à déplacer**, et le
projet reste utilisable comme projet Java simple si on ne fait rien.

Pour le piloter par Maven : clic droit sur le projet > `Configure > Convert to
Maven Project`. Un **M** apparaît sur l'icône du projet et `Maven Dependencies`
dans l'arbre (vide ici, le TP1 n'utilise que la bibliothèque standard).

Ensuite, clic droit > `Run As > Maven install` produit `target/tp1-1.0.0.jar`,
un **jar exécutable**. C'est la réponse « propre » à la partie 4 du sujet :

```sh
java -jar target/tp1-1.0.0.jar          # lance l'IHM Swing
java -cp target/tp1-1.0.0.jar tp1.Tp1   # lance la version console
```

Les versions des plugins Maven sont figées dans le `pom.xml` pour que la
construction marche aussi **sans connexion internet**.
