package edu.duke.zw255.battleship.shared;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class SmartComputerTextPlayer extends ComputerTextPlayer {
    // this is the set for coordinate more likely to hit
    public HashSet<Coordinate> priorityHitSet;
    public Set<Coordinate> hitCoordinateSet;
    public Coordinate toMove;
    public int moveTime=3;

    public SmartComputerTextPlayer(String name, BattleShipBoard theBoard, BufferedReader inputSource, PrintStream out,
            AbstractShipFactory<Character> shipFactory) {
        super(name, theBoard, inputSource, out, shipFactory);
        priorityHitSet = new HashSet<Coordinate>();
        hitCoordinateSet = new HashSet<Coordinate>();
    }

    @Override
    public void fireAction(Board<Character> enemyBoard) {
        while (true) {
            Coordinate c;
            if (priorityHitSet.size() > 0) {
                c = priorityHitSet.iterator().next();
                priorityHitSet.remove(c);
            } else {
                c = fireCoordinate.iterator().next();
            }

            fireCoordinate.remove(c);
            Ship<Character> ship = enemyBoard.fireAt(c);
            out.println("You fire at"+c.toString());

            if (ship != null) {
                out.println("Player " + name + " hit enemy " + ship.getName() + " at " + c.print() + "!");
                Coordinate[] toAdd = new Coordinate[4];
                toAdd[0] = new Coordinate(c.getRow() + 1, c.getColumn());
                toAdd[1] = new Coordinate(c.getRow() - 1, c.getColumn());
                toAdd[2] = new Coordinate(c.getRow(), c.getColumn() + 1);
                toAdd[3] = new Coordinate(c.getRow(), c.getColumn() - 1);
                for (Coordinate needCheck : toAdd) {
                    if (needCheck.valid(theBoard.getWidth(), theBoard.getHeight())
                            && fireCoordinate.contains(needCheck)) {
                        priorityHitSet.add(needCheck);
                    }
                }

            } else {
                out.println("Player " + name + " missed!");
            }
            // if (enemyBoard.isLose()) {
            // out.println("Player " + name + " have won!");
            // }
            break;
        }
    }

    @Override
    public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView) {
        while (true) {
            String myHeader = "Your ocean";   
            String enemyHeader = "Enemy's ocean";
            out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader));
            if (moveTime>0){
                for (Object o : ((BattleShipBoard) theBoard).enemyHits.keySet()) {
                    Coordinate currHit = (Coordinate) o;
                    if (hitCoordinateSet.contains(currHit) == false) {
                        toMove = currHit;
                        if (theBoard.shipAt(toMove)==null){
                            break;
                        }
                        moveAction();
                        moveTime-=1;
                        hitCoordinateSet.add(currHit);
                        return;
                    }
                }
            }

            fireAction(enemyBoard);
            break;

        }

    }

    @Override
    public void moveAction() {
        Ship<Character> olds = theBoard.shipAt(toMove);
        theBoard.removeShip(olds);
            while (true) {
                try {
                    Function<Placement, Ship<Character>> createFn = shipCreationFns.get(olds.getName());
                    Placement newPlacement=randomPlacement(olds.getName());
                    Ship<Character> news = createFn.apply(newPlacement);
                    String res = theBoard.tryAddShip(news);
                    // out.print(view.displayMyOwnBoard());
                    if (res != null) {
                        throw new IllegalArgumentException(res);
                    }
                    news.moveShip(olds);
                    out.println("move from"+toMove.toString()+"to"+newPlacement);
                    break;
                } catch (IllegalArgumentException e) {
                    out.println(e.getMessage());
                }
            }
        }


}
