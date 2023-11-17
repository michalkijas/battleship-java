package org.scrum.psd.battleship.ascii;

import org.scrum.psd.battleship.controller.GameController;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.*;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class Main {
    private static List<Ship> myFleet;
    private static List<Ship> enemyFleet;

    private static final Telemetry telemetry = new Telemetry();

    public static void main(String[] args) {
        telemetry.trackEvent("ApplicationStarted", "Technology", "Java");
        System.out.println(colorize("                                     |__", MAGENTA_TEXT()));
        System.out.println(colorize("                                     |\\/", MAGENTA_TEXT()));
        System.out.println(colorize("                                     ---", MAGENTA_TEXT()));
        System.out.println(colorize("                                     / | [", MAGENTA_TEXT()));
        System.out.println(colorize("                              !      | |||", MAGENTA_TEXT()));
        System.out.println(colorize("                            _/|     _/|-++'", MAGENTA_TEXT()));
        System.out.println(colorize("                        +  +--|    |--|--|_ |-", MAGENTA_TEXT()));
        System.out.println(colorize("                     { /|__|  |/\\__|  |--- |||__/", MAGENTA_TEXT()));
        System.out.println(colorize("                    +---------------___[}-_===_.'____                 /\\", MAGENTA_TEXT()));
        System.out.println(colorize("                ____`-' ||___-{]_| _[}-  |     |_[___\\==--            \\/   _", MAGENTA_TEXT()));
        System.out.println(colorize(" __..._____--==/___]_|__|_____________________________[___\\==--____,------' .7", MAGENTA_TEXT()));
        System.out.println(colorize("|                        Welcome to Battleship                         BB-61/", MAGENTA_TEXT()));
        System.out.println(colorize(" \\_________________________________________________________________________|", MAGENTA_TEXT()));
        System.out.println("");

        InitializeGame();

        StartGame();
    }

    private static void StartGame() {
        Scanner scanner = new Scanner(System.in);
        
        int round = 0;
        List<Position> playerShotsHistory = new ArrayList<>();
        List<Position> computerShotsHistory = new ArrayList<>();         

        System.out.print("\033[2J\033[;H");
        /* System.out.println("                  __");
        System.out.println("                 /  \\");
        System.out.println("           .-.  |    |");
        System.out.println("   *    _.-'  \\  \\__/");
        System.out.println("    \\.-'       \\");
        System.out.println("   /          _/");
        System.out.println("  |      _  /\" \"");
        System.out.println("  |     /_\'");
        System.out.println("   \\    \\_/");
        System.out.println("    \" \"\" \"\" \"\" \""); */

        do {
            round++;
            System.out.println(String.format("======================="));
            System.out.println(String.format("         ROUND %s      ",round));
            System.out.println(String.format("+----------+----------+"));
            System.out.println(String.format("|  Player  | Computer |"));
            System.out.println(String.format("+----------+----------+"));  
            System.out.println(String.format("| ABCDEFGH | ABCDEFGH |"));          

            //String printout = "";
            Position coordinates;
            for (int row = 1; row <= 8; row++){
                System.out.print(String.format("%s ",row));
                for (Letter column : Letter.values()){                    
                    coordinates = parsePosition(String.format("%s%s",column,row));
                    if (GameController.checkIsHit(myFleet, coordinates)){                         
                        if (computerShotsHistory.contains(coordinates)){
                            System.out.print(colorize("X",RED_TEXT())); //player's hit
                        } else {
                            System.out.print(colorize("O",GREEN_TEXT())); //player's not hit
                        }
                    } else {
                        System.out.print(colorize("~",BLUE_TEXT())); //empty
                    }
                }
                System.out.print(String.format(" %s ",row));
                for (Letter column : Letter.values()){                    
                    coordinates = parsePosition(String.format("%s%s",column,row));
                    if (playerShotsHistory.contains(coordinates)){  
                        if (GameController.checkIsHit(enemyFleet, coordinates)){
                            System.out.print(colorize("X",RED_TEXT())); //computer's hit
                        } else {
                            System.out.print(colorize("~",BLUE_TEXT())); //empty
                        }
                    } else {
                        System.out.print(colorize(" ",BLUE_TEXT())); //not checked
                    }
                }
                System.out.println(String.format(" %s",row));                
            }
            System.out.println(String.format("| ABCDEFGH | ABCDEFGH |"));
            System.out.println(String.format("+----------+----------+"));  

            System.out.println("Player, it's your turn.");
            System.out.print(colorize("Enter coordinates for your shot: ", YELLOW_TEXT()));

            Position position = parsePosition(scanner.next());
            playerShotsHistory.add(position);
            boolean isHit = GameController.checkIsHit(enemyFleet, position);            
            if (isHit) {
                beep();

                /* System.out.println("                \\         .  ./");
                System.out.println("              \\      .:\" \";'.:..\" \"   /");
                System.out.println("                  (M^^.^~~:.'\" \").");
                System.out.println("            -   (/  .    . . \\ \\)  -");
                System.out.println("               ((| :. ~ ^  :. .|))");
                System.out.println("            -   (\\- |  \\ /  |  /)  -");
                System.out.println("                 -\\  \\     /  /-");
                System.out.println("                   \\  \\   /  /"); */
            }

            System.out.println(isHit ? colorize("Yeah! Nice hit!",RED_TEXT()) : colorize("Miss.",BLUE_TEXT()));
            telemetry.trackEvent("Player_ShootPosition", "Position", position.toString(), "IsHit", Boolean.valueOf(isHit).toString());

            position = getRandomPosition();
            computerShotsHistory.add(position);
            isHit = GameController.checkIsHit(myFleet, position);
            System.out.println("");
            System.out.println(String.format("Computer shoot in %s%s and %s", position.getColumn(), position.getRow(), isHit ? colorize("hit your ship!",RED_TEXT()) : colorize("miss.",BLUE_TEXT())));
            System.out.println("");
            System.out.println("");
            telemetry.trackEvent("Computer_ShootPosition", "Position", position.toString(), "IsHit", Boolean.valueOf(isHit).toString());
            if (isHit) {
                beep();

                /* System.out.println("                \\         .  ./");
                System.out.println("              \\      .:\" \";'.:..\" \"   /");
                System.out.println("                  (M^^.^~~:.'\" \").");
                System.out.println("            -   (/  .    . . \\ \\)  -");
                System.out.println("               ((| :. ~ ^  :. .|))");
                System.out.println("            -   (\\- |  \\ /  |  /)  -");
                System.out.println("                 -\\  \\     /  /-");
                System.out.println("                   \\  \\   /  /"); */

            }
        } while (true);
    }

    private static void beep() {
        System.out.print("\007");
    }

    protected static Position parsePosition(String input) {
        Letter letter = Letter.valueOf(input.toUpperCase().substring(0, 1));
        int number = Integer.parseInt(input.substring(1));
        return new Position(letter, number);
    }

    private static Position getRandomPosition() {
        int rows = 8;
        int lines = 8;
        Random random = new Random();
        Letter letter = Letter.values()[random.nextInt(lines)];
        int number = random.nextInt(rows);
        Position position = new Position(letter, number);
        return position;
    }

    private static void InitializeGame() {
        InitializeMyFleet();

        Random random = new Random();
        int mapNumber = random.nextInt(6);

        InitializeEnemyFleet(mapNumber);
    }

    private static void InitializeMyFleet() {
        Scanner scanner = new Scanner(System.in);
        myFleet = GameController.initializeShips();

        System.out.println("Please position your fleet (Game board has size from A to H and 1 to 8) :");

        for (Ship ship : myFleet) {
            System.out.println("");
            System.out.println(String.format("Please enter the positions for the %s (size: %s)", ship.getName(), ship.getSize()));
            for (int i = 1; i <= ship.getSize(); i++) {
                System.out.println(colorize(String.format("Enter position %s of %s (i.e A3):", i, ship.getSize()), YELLOW_TEXT()));

                String positionInput = scanner.next();
                ship.addPosition(positionInput);
                telemetry.trackEvent("Player_PlaceShipPosition", "Position", positionInput, "Ship", ship.getName(), "PositionInShip", Integer.valueOf(i).toString());
            }
        }
    }

    private static void InitializeEnemyFleet(int mapNumber) {
        enemyFleet = GameController.initializeShips();

        switch (mapNumber){
            case 0:
                enemyFleet.get(0).getPositions().add(new Position(Letter.B, 4));
                enemyFleet.get(0).getPositions().add(new Position(Letter.B, 5));
                enemyFleet.get(0).getPositions().add(new Position(Letter.B, 6));
                enemyFleet.get(0).getPositions().add(new Position(Letter.B, 7));
                enemyFleet.get(0).getPositions().add(new Position(Letter.B, 8));

                enemyFleet.get(1).getPositions().add(new Position(Letter.E, 6));
                enemyFleet.get(1).getPositions().add(new Position(Letter.E, 7));
                enemyFleet.get(1).getPositions().add(new Position(Letter.E, 8));
                enemyFleet.get(1).getPositions().add(new Position(Letter.E, 9));

                enemyFleet.get(2).getPositions().add(new Position(Letter.A, 3));
                enemyFleet.get(2).getPositions().add(new Position(Letter.B, 3));
                enemyFleet.get(2).getPositions().add(new Position(Letter.C, 3));

                enemyFleet.get(3).getPositions().add(new Position(Letter.F, 8));
                enemyFleet.get(3).getPositions().add(new Position(Letter.G, 8));
                enemyFleet.get(3).getPositions().add(new Position(Letter.H, 8));

                enemyFleet.get(4).getPositions().add(new Position(Letter.B, 1));
                enemyFleet.get(4).getPositions().add(new Position(Letter.B, 2));//
                break;
            case 1:
                enemyFleet.get(0).getPositions().add(new Position(Letter.B, 1));
                enemyFleet.get(0).getPositions().add(new Position(Letter.B, 2));//
                enemyFleet.get(0).getPositions().add(new Position(Letter.B, 3));
                enemyFleet.get(0).getPositions().add(new Position(Letter.B, 4));
                enemyFleet.get(0).getPositions().add(new Position(Letter.B, 5));
                
                enemyFleet.get(1).getPositions().add(new Position(Letter.B, 8));
                enemyFleet.get(1).getPositions().add(new Position(Letter.C, 8));
                enemyFleet.get(1).getPositions().add(new Position(Letter.D, 8));
                enemyFleet.get(1).getPositions().add(new Position(Letter.E, 8));
                
                enemyFleet.get(2).getPositions().add(new Position(Letter.D, 1));
                enemyFleet.get(2).getPositions().add(new Position(Letter.D, 2));
                enemyFleet.get(2).getPositions().add(new Position(Letter.D, 3));
                
                enemyFleet.get(3).getPositions().add(new Position(Letter.F, 1));
                enemyFleet.get(3).getPositions().add(new Position(Letter.F, 2));
                enemyFleet.get(3).getPositions().add(new Position(Letter.F, 3));
                
                enemyFleet.get(4).getPositions().add(new Position(Letter.H, 2));
                enemyFleet.get(4).getPositions().add(new Position(Letter.H, 3));
                break;
            case 2:
                enemyFleet.get(0).getPositions().add(new Position(Letter.A, 2));
                enemyFleet.get(0).getPositions().add(new Position(Letter.B, 2));//
                enemyFleet.get(0).getPositions().add(new Position(Letter.C, 2));
                enemyFleet.get(0).getPositions().add(new Position(Letter.E, 2));
                enemyFleet.get(0).getPositions().add(new Position(Letter.F, 2));
                
                enemyFleet.get(1).getPositions().add(new Position(Letter.B, 4));
                enemyFleet.get(1).getPositions().add(new Position(Letter.C, 4));
                enemyFleet.get(1).getPositions().add(new Position(Letter.D, 4));
                enemyFleet.get(1).getPositions().add(new Position(Letter.E, 4));
                
                enemyFleet.get(2).getPositions().add(new Position(Letter.A, 6));
                enemyFleet.get(2).getPositions().add(new Position(Letter.B, 6));
                enemyFleet.get(2).getPositions().add(new Position(Letter.C, 6));
                
                enemyFleet.get(3).getPositions().add(new Position(Letter.A, 8));
                enemyFleet.get(3).getPositions().add(new Position(Letter.B, 8));
                enemyFleet.get(3).getPositions().add(new Position(Letter.C, 8));
                
                enemyFleet.get(4).getPositions().add(new Position(Letter.G, 1));
                enemyFleet.get(4).getPositions().add(new Position(Letter.H, 1));
                break;            
            case 3:
                enemyFleet.get(0).getPositions().add(new Position(Letter.E, 2));
                enemyFleet.get(0).getPositions().add(new Position(Letter.E, 3));
                enemyFleet.get(0).getPositions().add(new Position(Letter.E, 4));
                enemyFleet.get(0).getPositions().add(new Position(Letter.E, 5));
                enemyFleet.get(0).getPositions().add(new Position(Letter.E, 6));
                
                enemyFleet.get(1).getPositions().add(new Position(Letter.A, 3));
                enemyFleet.get(1).getPositions().add(new Position(Letter.A, 4));
                enemyFleet.get(1).getPositions().add(new Position(Letter.A, 6));
                enemyFleet.get(1).getPositions().add(new Position(Letter.A, 7));
                
                enemyFleet.get(2).getPositions().add(new Position(Letter.G, 2));
                enemyFleet.get(2).getPositions().add(new Position(Letter.G, 3));
                enemyFleet.get(2).getPositions().add(new Position(Letter.G, 4));
                
                enemyFleet.get(3).getPositions().add(new Position(Letter.G, 6));
                enemyFleet.get(3).getPositions().add(new Position(Letter.G, 7));
                enemyFleet.get(3).getPositions().add(new Position(Letter.G, 8));
                
                enemyFleet.get(4).getPositions().add(new Position(Letter.B, 1));
                enemyFleet.get(4).getPositions().add(new Position(Letter.B, 2));//
                break;
            case 4:
                enemyFleet.get(0).getPositions().add(new Position(Letter.A, 4));
                enemyFleet.get(0).getPositions().add(new Position(Letter.B, 4));
                enemyFleet.get(0).getPositions().add(new Position(Letter.C, 4));
                enemyFleet.get(0).getPositions().add(new Position(Letter.D, 4));
                enemyFleet.get(0).getPositions().add(new Position(Letter.E, 4));
                
                enemyFleet.get(1).getPositions().add(new Position(Letter.E, 2));
                enemyFleet.get(1).getPositions().add(new Position(Letter.F, 2));
                enemyFleet.get(1).getPositions().add(new Position(Letter.G, 2));
                enemyFleet.get(1).getPositions().add(new Position(Letter.H, 2));
                
                
                enemyFleet.get(2).getPositions().add(new Position(Letter.A, 2));
                enemyFleet.get(2).getPositions().add(new Position(Letter.B, 2));//
                enemyFleet.get(2).getPositions().add(new Position(Letter.C, 2));
                
                enemyFleet.get(3).getPositions().add(new Position(Letter.E, 6));
                enemyFleet.get(3).getPositions().add(new Position(Letter.F, 6));
                enemyFleet.get(3).getPositions().add(new Position(Letter.G, 6));
                
                enemyFleet.get(4).getPositions().add(new Position(Letter.B, 7));
                enemyFleet.get(4).getPositions().add(new Position(Letter.B, 8));
                break;
            case 5:
                enemyFleet.get(0).getPositions().add(new Position(Letter.D, 8));
                enemyFleet.get(0).getPositions().add(new Position(Letter.E, 8));
                enemyFleet.get(0).getPositions().add(new Position(Letter.F, 8));
                enemyFleet.get(0).getPositions().add(new Position(Letter.G, 8));
                enemyFleet.get(0).getPositions().add(new Position(Letter.H, 8));
                
                enemyFleet.get(1).getPositions().add(new Position(Letter.H, 3));
                enemyFleet.get(1).getPositions().add(new Position(Letter.H, 4));
                enemyFleet.get(1).getPositions().add(new Position(Letter.H, 5));
                enemyFleet.get(1).getPositions().add(new Position(Letter.H, 6));
                
                enemyFleet.get(2).getPositions().add(new Position(Letter.B, 2));//
                enemyFleet.get(2).getPositions().add(new Position(Letter.B, 3));
                enemyFleet.get(2).getPositions().add(new Position(Letter.B, 4));
                
                enemyFleet.get(3).getPositions().add(new Position(Letter.A, 6));
                enemyFleet.get(3).getPositions().add(new Position(Letter.A, 7));
                enemyFleet.get(3).getPositions().add(new Position(Letter.A, 8));
                
                enemyFleet.get(4).getPositions().add(new Position(Letter.C, 1));
                enemyFleet.get(4).getPositions().add(new Position(Letter.D, 1));
                break;
        }        
    }
}
