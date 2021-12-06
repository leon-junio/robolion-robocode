package robolion;

import java.awt.Color;
import robocode.*;

/**
 *
 * @author junio
 */
public class LeonBot extends AdvancedRobot {

    double previousEnergy = 100;
    int movementDirection = 1;
    boolean movingForward = true;
    int gunDirection = 1;
    double distance;
    
    public void setCores() {
        setColors(Color.blue, Color.red, Color.darkGray);
    }

    public void run() {
        setCores();
        setTurnGunRight(99999);
    }

    public void onScannedRobot(
            ScannedRobotEvent e) {
        // Stay at right angles to the opponent
        setTurnRight(e.getBearing() + 90
                - 30 * movementDirection);

        // If the bot has small energy drop,
        // assume it fired
        double changeInEnergy
                = previousEnergy - e.getEnergy();
        if (changeInEnergy > 0
                && changeInEnergy <= 3) {
            // Dodge!
            movementDirection
                    = -movementDirection;
            setAhead((e.getDistance() / 4 + 25) * movementDirection);
        }
        // When a bot is spotted,
        // sweep the gun and radar
        gunDirection = -gunDirection;
        setTurnGunRight(99999 * gunDirection);
        distance = e.getDistance();
        // Fire directly at target
        tiroFatal(changeInEnergy);
        

        // Track the energy level
        previousEnergy = e.getEnergy();
    }

    public void onHitByBullet(HitByBulletEvent e) {
        // Replace the next line with any behavior you would like
        //retornar();
    }

    public void onHitWall(HitWallEvent e) {
        reverseDirecao();
    }
    public void reverseDirecao() {
        if (this.movingForward) {
            setBack(40000);
            movingForward = false;
        } else {
            setAhead(40000);
            movingForward = true;
        }
    }

    public void onHitRobot(HitRobotEvent e) {
        if (e.isMyFault()) {
            reverseDirecao();
        }
    }

    public void tiroFatal(double EnergiaIni) {
        double Tiro = (EnergiaIni / 4) + .1;
        if(distance>150){
           
            fire(Tiro);
            
        }else{
            //track();
        }
        
    }

}
