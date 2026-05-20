package ui;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import model.Team;
import model.state.State;
import model.action.Move;
import model.tokens.Token;
import model.tokens.Pawn;
import model.tokens.Ring;
import coordinate.*;

public class CUIMain {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 1. Génération de la géométrie complète du plateau YINSH (taille 5)
        Map<Coordinate, Token> plateauInitial = State.genereTab(5);
        
        // Extraction et mélange des coordonnées pour placer les anneaux aléatoirement
        List<Coordinate> casesValides = new ArrayList<>(plateauInitial.keySet());
        Collections.shuffle(casesValides);
        
        // 2. Placement initial des 5 anneaux BLANCS et 5 anneaux NOIRS
        int anneauxBlancs = 0;
        int anneauxNoirs = 0;
        int indexCase = 0;
        
        while (anneauxBlancs < 5 || anneauxNoirs < 5) {
            Coordinate caseCible = casesValides.get(indexCase++);
            
            if (anneauxBlancs < 5) {
                plateauInitial.put(caseCible, new Ring(Team.WHITE));
                anneauxBlancs++;
            } else if (anneauxNoirs < 5) {
                plateauInitial.put(caseCible, new Ring(Team.BLACK));
                anneauxNoirs++;
            }
        }
        
        // 3. Création de l'état initial (WHITE commence)
        State state = new State(plateauInitial, Team.WHITE, new ArrayList<>());
        
        Mode modeActuel = Mode.FLAT;
        boolean modeChoisi = false;
        
        System.out.println("=== CONFIGURATION DU PLATEAU DE JEU ===");
        while (!modeChoisi) {
            System.out.println("Choisissez le mode d'affichage (1 pour FLAT, 2 pour POINTY) :");
            String choix = scanner.nextLine().trim();
            if (choix.equals("1")) {
                modeActuel = Mode.FLAT;
                modeChoisi = true;
            } else if (choix.equals("2")) {
                modeActuel = Mode.POINTY;
                modeChoisi = true;
            } else {
                System.out.println("Choix invalide. Veuillez taper 1 ou 2.");
            }
        }
        
        boolean gameRunning = true;
        System.out.println("\n--- DÉBUT DE LA PARTIE ---");
        System.out.println("Orientation active du terrain : " + modeActuel);
        System.out.println("Format de commande attendu : NumAnneau Distance Direction (ex: 1 2 NE)");
        System.out.println("Directions valides : NO, NE, E, O, SO, SE");
        System.out.println("Tapez 'exit' pour quitter.\n");

        while (gameRunning) {
            // Affichage graphique du plateau
            afficherPlateauDynamique(state.board(), modeActuel);
            
            Team tourActuel = state.turn();
            System.out.println("\n========================================");
            System.out.println("C'EST AU TOUR DU JOUEUR : " + tourActuel);
            System.out.println("========================================");
            
            // Récupération de la liste des anneaux appartenant au joueur actuel
            Map<Team, List<Coordinate>> tousLesAnneaux = state.rings();
            List<Coordinate> anneauxJoueur = tousLesAnneaux.get(tourActuel);
            
            if (anneauxJoueur == null || anneauxJoueur.isEmpty()) {
                System.out.println("Erreur : Plus aucun anneau sur le plateau pour " + tourActuel);
                gameRunning = false;
                break;
            }
            
            // Affichage de la liste des anneaux disponibles pour ce joueur
            System.out.println("Vos anneaux disponibles :");
            for (int i = 0; i < anneauxJoueur.size(); i++) {
                Coordinate c = anneauxJoueur.get(i);
                if (c instanceof CoordinateCube cube) {
                    System.out.println("  Anneau n°" + (i + 1) + " : coordonnées (q=" + cube.getQ() + ", r=" + cube.getR() + ")");
                }
            }
            
            System.out.print("\nAction (NumAnneau Distance Direction) > ");
            String coup = scanner.nextLine().trim();
            
            if (coup.equalsIgnoreCase("exit")) {
                gameRunning = false;
                System.out.println("Fin de l'application.");
            } else {
                try {
                    String[] segments = coup.split("\\s+");
                    if (segments.length == 3) {
                        int numAnneau = Integer.parseInt(segments[0]) - 1; // Indexation 0-4
                        int distance = Integer.parseInt(segments[1]);
                        Direction dir = Direction.valueOf(segments[2].toUpperCase());
                        
                        // Validation du numéro d'anneau choisi
                        if (numAnneau < 0 || numAnneau >= anneauxJoueur.size()) {
                            System.out.println("Erreur : Numéro d'anneau invalide. Choisissez un nombre entre 1 et " + anneauxJoueur.size());
                            continue;
                        }
                        
                        if (distance <= 0) {
                            System.out.println("Erreur : La distance doit être supérieure à 0.");
                            continue;
                        }
                        
                        // Récupération de l'anneau sélectionné
                        Coordinate coordDepart = anneauxJoueur.get(numAnneau);
                        
                        // Calcul de la destination pas à pas
                        Coordinate coordArrivee = coordDepart;
                        for (int k = 0; k < distance; k++) {
                            coordArrivee = coordArrivee.toDir(Mode.POINTY, dir);
                        }
                        
                        // Envoi de l'action de déplacement à ton Record State
                        Move moveAction = new Move(coordDepart, coordArrivee);
                        state = (State) state.move(moveAction);
                        
                        System.out.println("\n[OK] Déplacement de l'anneau n°" + (numAnneau + 1) + " réussi !\n");
                        
                    } else {
                        System.out.println("Erreur : Format invalide. Exemple attendu : '1 2 NE'");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Erreur : Le numéro d'anneau et la distance doivent être des entiers.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Action refusée : " + e.getMessage());
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Erreur de déplacement : " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Erreur : " + e.getMessage());
                }
            }
        }
        scanner.close();
    }
    
    public static void afficherPlateauDynamique(Map<Coordinate, Token> plateau, Mode mode) {
        int maxLignes = (mode == Mode.FLAT) ? 11 : 21;
        int maxColonnes = (mode == Mode.FLAT) ? 19 : 11;
        
        char[][] affichage = new char[maxLignes][maxColonnes];
    
        for(int i = 0; i < maxLignes; i++) {
            for(int j = 0; j < maxColonnes; j++) {
                affichage[i][j] = ' ';
            }
        }
        
        for (Coordinate c : plateau.keySet()) {
            if (c instanceof CoordinateCube cube) {
                int q = cube.getQ();
                int r = cube.getR();
                
                int targetY, targetX;
                if (mode == Mode.FLAT) {
                    targetY = r + 5;
                    targetX = 2 * q + r + 9;
                } else {
                    targetY = 2 * r + q + 10;
                    targetX = q + 5;
                }
                
                if(targetY >= 0 && targetY < maxLignes && targetX >= 0 && targetX < maxColonnes) {
                    affichage[targetY][targetX] = '_';
                }
            }
        }
        
        for (Map.Entry<Coordinate, Token> entry : plateau.entrySet()) {
            Coordinate coord = entry.getKey();
            Token token = entry.getValue();
            
            if (token != null && coord instanceof CoordinateCube cube) {
                int q = cube.getQ();
                int r = cube.getR();
                
                int targetY, targetX;
                if (mode == Mode.FLAT) {
                    targetY = r + 5;
                    targetX = 2 * q + r + 9;
                } else {
                    targetY = 2 * r + q + 10;
                    targetX = q + 5;
                }
                
                if(targetY >= 0 && targetY < maxLignes && targetX >= 0 && targetX < maxColonnes) {
                    char symbole = '_';
                    if (token instanceof Pawn) {
                        symbole = (token.getTeam() == Team.WHITE) ? '.' : 'x';
                    } else if (token instanceof Ring) {
                        symbole = (token.getTeam() == Team.WHITE) ? 'o' : 'O';
                    }
                    affichage[targetY][targetX] = symbole;
                }
            }
        }
        
        for(int i = 0; i < maxLignes; i++) {
            System.out.println(new String(affichage[i]));
        }
    }
}