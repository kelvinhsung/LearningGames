package Tutorials;

import java.awt.event.KeyEvent;

import GhostLight.Interface.GhostLightInterface;
import GhostLight.Interface.InteractableObject;
import GhostLight.Interface.InteractableObject.ObjectType;

public class Tutorial_08 extends GhostLightInterface {

    @Override
    public void initialize() {
        // Temp fix to adjust starting health values
        InteractableObject.setDefualtHealth(10);
        gameState.setHealth(5);
        gameState.setScore(9001);
        gameState.setLightPower(0.45f);

        // creating a 2D array of monsters starts here
        InteractableObject[][] monsters = new InteractableObject[2][5];
        objectGrid.setObjectGrid(monsters);

        // Let's create a Frankenstein:
        InteractableObject newby = new InteractableObject();

        newby.setObjectType(ObjectType.FRANKENSTEIN, true); 
        
        newby.setMaxHealth(5);
        newby.setCurrentHealth(2);
        newby.setScore(9001);
        // Revealed means that the enemies true for is shown and when revealed
        // the
        // enemy will take whatever action it does when it is revealed
        // Partial revealed means that its
        // "Friendliness is hinted at to the player"
        // Pumpkins turn green everything else turns red
        newby.setRevealStatus(true);

        // Now a pumpkin
        InteractableObject m2 = new InteractableObject(ObjectType.PUMPKIN, 5, 3, 100);
        m2.setRevealStatus(true);

        // Add an enemy to the set at (Row 0, Colunn 3)
        monsters[0][3] = newby;
        monsters[1][3] = m2;

        // and finally a cat:
        monsters[1][4] = new InteractableObject(ObjectType.CAT, 4, 4, 100);
        monsters[1][4].setRevealStatus(true);
        // creating a 2D array of monsters ends here
    }

    @Override
    public void update() {
        // detecting where the light is shining starts here
        if (keyboard.isButtonTapped(KeyEvent.VK_SPACE)) {
            InteractableObject[] affected = light.getTargetedEnemies(objectGrid);
            // Activating the light returns an array that contains the enemies
            // in the objectGrid that were touched by the light
            if (affected != null) { // This array is not guaranteed to exist
                for (int loop = 0; loop < affected.length; loop++) {
                    if (affected[loop] != null) { // nor is this array
                                                  // guaranteed to be full
                        affected[loop].setCurrentHealth(0);
                    }
                }
            }
        }
        // detecting where the light is shining ends here

        // Ghost bOOst starts here
        // increase all monsters' health by a bit
        if (keyboard.isButtonTapped(KeyEvent.VK_UP)) {
            InteractableObject[] monsters = findMonsterObjects(objectGrid.getObjectGrid());
            // float[] healthArray = primitiveGrid.getHealthArray()[0];
            // remember that loc.length may be 0
            for (int i = 0; i < monsters.length; i++) {
                InteractableObject nextMonster = monsters[i];
                float health = nextMonster.getHealth();
                int newHealth = (int) Math.min(health + 1, 10);
                nextMonster.setCurrentHealth(newHealth);
                System.out.print("  "
                        + nextMonster
                        + ": "
                        + nextMonster.getHealth());
            }
            System.out.println();
        }
        // Ghost bOOst ends here

        // Ghost bUst starts here
        // decrease all monsters' health by a bit
        if (keyboard.isButtonTapped(KeyEvent.VK_DOWN)) {
            InteractableObject[] monsters = findMonsterObjects(objectGrid.getObjectGrid());
            // float[] healthArray = primitiveGrid.getHealthArray()[0];
            // remember that loc.length may be 0
            for (int i = 0; i < monsters.length; i++) {
                InteractableObject nextMonster = monsters[i];
                float health = nextMonster.getHealth();
                int newHealth = (int) Math.max(health - 1, 0);
                nextMonster.setCurrentHealth(newHealth);
                System.out.print("  "
                        + nextMonster
                        + ": "
                        + nextMonster.getHealth());
            }
            System.out.println();
        }
        // Ghost bUst ends here

        // using the numberOfMonsters method starts here
        InteractableObject[][] monsterArray = objectGrid.getObjectGrid();
        
        int numMonsters = 0;
        numMonsters = this.numberOfMonsters(monsterArray);

        System.out.print("There are "
                + numMonsters
                + " monsters on screen; ");
        // using the numberOfMonsters method ends here

        // using the averageMonsterHealth method starts here
        float avgHealth = 0f;
        avgHealth = averageMonsterHealth(monsterArray);
        System.out.println("Average health: "
                + avgHealth);
        // using the averageMonsterHealth method ends here

        if (keyboard.isButtonTapped(KeyEvent.VK_A)
                || keyboard.isButtonTapped(KeyEvent.VK_LEFT)) {
            light.setPosition(light.getPosition() - 1); // Decrementing position
        } else if (keyboard.isButtonTapped(KeyEvent.VK_D)
                || keyboard.isButtonTapped(KeyEvent.VK_RIGHT)) {
            light.setPosition(light.getPosition() + 1); // incrementing position
        }
    }

    // method to find occupied array elements starts here
    public InteractableObject[] findMonsterObjects(InteractableObject[][] monsterArray) {
        int num = numberOfMonsters(monsterArray);
        InteractableObject[] monsters = new InteractableObject[num];
        if (num == 0)
            return monsters; // unusual but legal

        int next = 0;
        for (int row = 0; row < monsterArray.length; row++) {
            for (int col = 0; col < monsterArray[row].length; col++) {
                if (monsterArray[row][col] != null)
                    monsters[next++] = monsterArray[row][col];
            }
        }
        return monsters; // unusual but legal
    }
    // method to find occupied array elements ends here

    // method to sum array elements starts here
    public int numberOfMonsters(InteractableObject[][] monsters) {

        int numMonsters = 0;
        for (int row = 0; row < monsters.length; row++)
            for (int col = 0; col < monsters[row].length; col++)
                if (monsters[row][col] != null)
                    numMonsters++;
        return numMonsters;
    }
    // method to sum array elements ends here

    // method to average array elements starts here
    public float averageMonsterHealth(InteractableObject[][] monsters) {

        int numMonsters = numberOfMonsters(monsters);

        float totalHealth = 0.0f;
        for (int row = 0; row < monsters.length; row++)
            for (int col = 0; col < monsters[row].length; col++)
                if (monsters[row][col] != null)
                    totalHealth += monsters[row][col].getHealth();

        float avgHealth;
        if (numMonsters > 0)
            avgHealth = totalHealth
                    / numMonsters;
        else
            avgHealth = 0f;

        return avgHealth;
    }
    // method to average array elements ends here

    @Override
    public void end() {
        // TODO Auto-generated method stub
    }
}