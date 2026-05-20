package ui;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import model.Team;
import model.tokens.Token;
import model.tokens.Pawn;
import model.tokens.Ring;
import coordinate.*;

public class CUIMain {
	
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Coordinate, Token> plateauTest = new HashMap<>();
        java.util.Random rand = new java.util.Random();
        
        Mode modeActuel = Mode.FLAT;
        boolean modeChoisi = false;
        
        System.out.println("=== CONFIGURATION DU PLATEAU ===");
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
        
        int pionsPlaces = 0;
        while (pionsPlaces < 10) {
            int q = rand.nextInt(11) - 5;
            int r = rand.nextInt(11) - 5;
            int s = -q - r;

            if (Math.abs(q) <= 5 && Math.abs(r) <= 5 && Math.abs(s) <= 5) {
                CoordinateCube coord = new CoordinateCube(q, r, s);
                
                if (!plateauTest.containsKey(coord)) {
                    Team team = (pionsPlaces % 2 == 0) ? Team.WHITE : Team.BLACK;
                    plateauTest.put(coord, new Pawn(team));
                    pionsPlaces++;
                }
            }
        }
        
        int jetonsPlaces = 0;
        while (jetonsPlaces < 20) {
            int q = rand.nextInt(11) - 5;
            int r = rand.nextInt(11) - 5;
            int s = -q - r;
            
            if (Math.abs(q) <= 5 && Math.abs(r) <= 5 && Math.abs(s) <= 5) {
                CoordinateCube coord = new CoordinateCube(q, r, s);
                if (!plateauTest.containsKey(coord)) {
                    Token nouveauJeton;
                    if (jetonsPlaces < 5) nouveauJeton = new Ring(Team.WHITE);
                    else if (jetonsPlaces < 10) nouveauJeton = new Ring(Team.BLACK);
                    else if (jetonsPlaces < 15) nouveauJeton = new Pawn(Team.WHITE);
                    else nouveauJeton = new Pawn(Team.BLACK);
                    
                    plateauTest.put(coord, nouveauJeton);
                    jetonsPlaces++;
                }
            }
        }
        
        plateauTest.put(new CoordinateCube(0, 0, 0), new Pawn(Team.WHITE));
        plateauTest.put(new CoordinateCube(1, 0, -1), new Pawn(Team.BLACK));

        boolean gameRunning = true;
        System.out.println("Orientation active du terrain : " + modeActuel);
        System.out.println("Format: q_dep r_dep Direction (ex: 0 0 NE)");
        System.out.println("Tapez 'exit' pour quitter.\n");

        while (gameRunning) {
            afficherPlateauDynamique(plateauTest, modeActuel);
            
            System.out.print("\nDéplacement > ");
            String coup = scanner.nextLine();
            
            if (coup.trim().equalsIgnoreCase("exit")) {
                gameRunning = false;
            } else {
                try {
                    String[] p = coup.split(" ");
                    if (p.length == 3) {
                        int q1 = Integer.parseInt(p[0]);
                        int r1 = Integer.parseInt(p[1]);
                        Direction dir = Direction.valueOf(p[2].toUpperCase());
                        
                        Coordinate vraieCleDepart = null;
                        for (Coordinate c : plateauTest.keySet()) {
                            if (c instanceof CoordinateCube cube && cube.getQ() == q1 && cube.getR() == r1) {
                                vraieCleDepart = c;
                                break;
                            }
                        }
                        
                        if (vraieCleDepart != null) {
                            Token jeton = plateauTest.remove(vraieCleDepart);
                            Coordinate coordArrivee = vraieCleDepart.toDir(modeActuel, dir);
                            plateauTest.put(coordArrivee, jeton);
                            System.out.println("Déplacement vers " + dir + " réussi.");
                        } else {
                            System.out.println("Erreur : Aucun pion trouvé en (" + q1 + "," + r1 + ").");
                        }
                    } else {
                        System.out.println("Erreur : Format attendu 'q r Direction'");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Erreur : Direction invalide (ex: NO, NE, E, O, N, S, SO, SE)");
                } catch (Exception e) {
                    System.out.println("Erreur : " + e.getMessage());
                }
            }
        }
        scanner.close();
    }
	
	public static void afficherPlateauDynamique(Map<coordinate.Coordinate, Token> plateau, Mode mode) {
		int maxLignes = (mode == Mode.FLAT) ? 11 : 21;
		int maxColonnes = (mode == Mode.FLAT) ? 19 : 11;
		
		char[][] affichage = new char[maxLignes][maxColonnes];
	
		for(int i = 0; i < maxLignes; i++) {
			for(int j = 0; j < maxColonnes; j++) {
				affichage[i][j] = ' ';
			}
		}
		
		for (int q = -5; q <= 5; q++) {
			for (int r = -5; r <= 5; r++) {
				int s = -q - r;
				if (Math.abs(q) <= 5 && Math.abs(r) <= 5 && Math.abs(s) <= 5) {
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
		}
		
		if (plateau != null) {
			for (Map.Entry<coordinate.Coordinate, Token> entry : plateau.entrySet()) {
				coordinate.Coordinate coord = entry.getKey();
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
						char symbole = ' ';
						if (token instanceof Pawn) {
							symbole = (token.getTeam() == Team.WHITE) ? '.' : 'x';
						} else if (token instanceof Ring) {
							symbole = (token.getTeam() == Team.WHITE) ? 'o' : 'O';
						}
						affichage[targetY][targetX] = symbole;
					}
				}
			}
		}
		
		for(int i = 0; i < maxLignes; i++) {
			System.out.println(new String(affichage[i]));
		}
	}
}