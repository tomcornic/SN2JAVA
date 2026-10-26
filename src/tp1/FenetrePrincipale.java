package tp1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Partie 5 du sujet : l'IHM Swing autour du programme de résolution.
 *
 * <p>Une {@link JFrame} dotée d'une barre de menu dont les options sont des
 * {@link JMenuItem} (et non des {@link JMenu}), afin de pouvoir intercepter le
 * clic :</p>
 * <ul>
 * <li><b>Lancer le programme</b> : ouvre la boîte de saisie, instancie un
 * {@link ResolEq}, calcule et affiche les racines dans le panneau,</li>
 * <li><b>Affichage en mode dessin</b> (partie 7) : trace la parabole,</li>
 * <li><b>Aide</b> : ouvre une boîte de dialogue,</li>
 * <li><b>Quitter</b> : ferme l'application.</li>
 * </ul>
 *
 * <p>Cette classe est volontairement écrite dans le style produit par
 * WindowBuilder (données membres, constructeur qui assemble l'IHM, écouteurs en
 * classes anonymes) : on peut l'ouvrir avec <i>Open With &gt; WindowBuilder
 * Editor</i> et continuer à la modifier à la souris.</p>
 */
public class FenetrePrincipale extends JFrame implements EcouteurCoefficients {

	private static final long serialVersionUID = 1L;

	/** Le panneau graphique, donnée membre de la fenêtre (partie 5 puis 7). */
	private MonPanel panneau;

	private JCheckBoxMenuItem optionNonModale;
	private BoiteSaisie boiteNonModale;

	/** Lancement de l'application. */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					FenetrePrincipale fenetre = new FenetrePrincipale();
					fenetre.setVisible(true);
				} catch (Exception erreur) {
					erreur.printStackTrace();
				}
			}
		});
	}

	public FenetrePrincipale() {
		setTitle("TP1 - Equation du second degre");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);

		panneau = new MonPanel();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panneau, BorderLayout.CENTER);

		setJMenuBar(construireMenu());
	}

	private JMenuBar construireMenu() {
		JMenuBar barre = new JMenuBar();
		JMenu menu = new JMenu("Programme");
		barre.add(menu);

		JMenuItem optionLancer = new JMenuItem("Lancer le programme");
		optionLancer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evenement) {
				lancerLeProgramme();
			}
		});
		menu.add(optionLancer);

		JMenuItem optionDessin = new JMenuItem("Affichage en mode dessin");
		optionDessin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evenement) {
				afficherEnModeDessin();
			}
		});
		menu.add(optionDessin);

		optionNonModale = new JCheckBoxMenuItem("Saisie en boite non modale");
		menu.add(optionNonModale);
		menu.addSeparator();

		JMenuItem optionAide = new JMenuItem("Aide");
		optionAide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evenement) {
				afficherAide();
			}
		});
		menu.add(optionAide);

		JMenuItem optionQuitter = new JMenuItem("Quitter");
		optionQuitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evenement) {
				quitter();
			}
		});
		menu.add(optionQuitter);

		return barre;
	}

	// ------------------------------------------------------------------
	// Actions des options du menu
	// ------------------------------------------------------------------

	/** Saisie des coefficients puis calcul et affichage des racines. */
	private void lancerLeProgramme() {
		if (optionNonModale.isSelected()) {
			// Partie 6 : la boite reste ouverte et nous rappelle a chaque calcul
			if (boiteNonModale == null || !boiteNonModale.isDisplayable()) {
				boiteNonModale = new BoiteSaisie(this);
			} else {
				boiteNonModale.toFront();
			}
			return;
		}

		BoiteSaisie boite = new BoiteSaisie(); // modale : on attend sa fermeture
		if (boite.estValide()) {
			coefficientsSaisis(boite.getA(), boite.getB(), boite.getC());
		}
	}

	/** Reçoit les coefficients, qu'ils viennent de la boîte modale ou non modale. */
	@Override
	public void coefficientsSaisis(double a, double b, double c) {
		ResolEq equation = new ResolEq(a, b, c);
		equation.resoudre();
		equation.afficher(); // trace aussi dans la console
		panneau.afficherRacines(equation);
	}

	private void afficherEnModeDessin() {
		if (!panneau.aUneEquation()) {
			JOptionPane.showMessageDialog(this, "Lance d'abord le programme pour saisir une equation.",
					"Rien a tracer", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		panneau.tracerCourbe();
	}

	private void afficherAide() {
		String aide = """
				TP1 - Equation du second degre a.x^2 + b.x + c = 0

				Menu Programme :
				  - Lancer le programme : saisie de a, b et c puis calcul des racines
				  - Affichage en mode dessin : trace la parabole et marque les racines
				  - Saisie en boite non modale : la boite de saisie reste ouverte
				  - Quitter : ferme l'application

				Rappel : si delta > 0 il y a deux racines reelles, si delta = 0 une
				racine double, et si delta < 0 deux racines complexes conjuguees.""";
		JOptionPane.showMessageDialog(this, aide, "Aide", JOptionPane.INFORMATION_MESSAGE);
	}

	private void quitter() {
		dispose();
		System.exit(0);
	}

	/** Accesseur utile si l'on veut manipuler le panneau depuis l'extérieur. */
	public JPanel getPanneau() {
		return panneau;
	}
}
