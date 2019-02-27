import greenfoot.Actor;
import greenfoot.GreenfootImage;
import greenfoot.World;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;


public class Inventory extends Actor implements Fixed {
    // TODO new Inventory Screen image
    // TODO implement pick limit
    // TODO better colors/Item background  (#FFD700?)
    // TODO make "switchTab-Buttons" look good
    // TODO ItemInfo displayed when clicked and/or mouse hovers over it
    // TODO This will probably never happen.....drag and drop Items to respective slots -> CREATE SLOTS
    private Player p;
    private World world;
    private int inventoryTab = 0;
    private LinkedList<Button>   buttonList;
    private LinkedList<Pickable> allItems;
    private LinkedList<Pickable> ArmorList;
    private LinkedList<Pickable> WeaponList;
    private LinkedList<Pickable> ItemList;
    private GreenfootImage InventoryScreen  = new GreenfootImage("images/Hud_Menu_Images/MyInventoryV3.png");
    private GreenfootImage leftArrowClicked     = new GreenfootImage("images/Arrows/Arrow_left_aktive.png");
    private GreenfootImage leftArrowNotClicked  = new GreenfootImage("images/Arrows/Arrow_left.png");
    private GreenfootImage rightArrowClicked    = new GreenfootImage("images/Arrows/Arrow_right_aktive.png");
    private GreenfootImage rightArrowNotClicked = new GreenfootImage("images/Arrows/Arrow_right.png");


    protected void addedToWorld(World world) {
        ArmorList  = new LinkedList<>();
        WeaponList = new LinkedList<>();
        ItemList   = new LinkedList<>();
        sortItems(p);
        createArrow("left");
        createArrow("right");
    }

    public Inventory(Player p, World world){
        this.p = p;
        this.world = world;
        buttonList = new LinkedList<>();
    }

    public void act(){
        InventoryScreen.clear();
        InventoryScreen  = new GreenfootImage("images/Hud_Menu_Images/MyInventoryV3.png");
        setImage(InventoryScreen);
        drawTabFonts();
        drawCurrentTab();

        mouseAreas();
    }

    private void drawTabFonts(){
        String armor = "Armor";
        String weapons = "Weapons";
        String items = "Items";
        InventoryScreen.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
        InventoryScreen.setColor(Color.WHITE);
        InventoryScreen.drawString(weapons,   470,175);
        InventoryScreen.drawString(armor,     580,175);
        InventoryScreen.drawString(items,     680,175);
        InventoryScreen.setColor(Color.RED);
        if(inventoryTab == 0){
            InventoryScreen.drawString(weapons,   470,175);
        }else if(inventoryTab == 1){
            InventoryScreen.drawString(armor,     580,175);
        }else if(inventoryTab == 2){
            InventoryScreen.drawString(items,     680,175);
        }
    }
    private void sortItems(Player p){
        allItems = p.getInventory();
        Iterator<Pickable> allItemsIT = allItems.iterator();
        if (!allItemsIT.hasNext()) {
            return;
        }
        for(Pickable item :allItems){
            if(item.getItemType().equals("Weapon")) {
                WeaponList.add(item);
            }else if(item.getItemType().equals("Armor")){
                ArmorList.add(item);
            }else {
                ItemList.add(item);
            }
        }
    }
    private void drawCurrentTab(){
        if(inventoryTab == 0){
            drawTab(WeaponList);
        }else if(inventoryTab == 1){
            drawTab(ArmorList);
        }else if(inventoryTab == 2){
            drawTab(ItemList);
        }else{
            setInventoryTab(0);
        }
    }
    private void drawTab(LinkedList<Pickable> itemsToDraw){
        int drawAtX = 395;
        int drawAtY = 195;
        int itemsDrawn = 0;
        if(itemsDrawn == 7){
            drawAtY = drawAtY + 52;
            drawAtX = drawAtX - 52*7;
            itemsDrawn = 0;
            System.out.println(itemsDrawn);
        }
        for (Pickable item: itemsToDraw) {
            drawItemBase();
            InventoryScreen.drawImage(item.getItemImage(), drawAtX, drawAtY);
            drawAtX = drawAtX + 52;
            itemsDrawn++;
        }
    }
    private void drawItemBase(){
        InventoryScreen.setColor(Color.cyan);
        InventoryScreen.fillRect(395,195, 52,52);
    }
    private void createArrow(String position){
        Button button;
        GreenfootImage buttonImgUnClicked;
        GreenfootImage buttonImgClicked;
            if(position.equals("left")){
                buttonImgUnClicked = leftArrowNotClicked;
                buttonImgClicked = leftArrowClicked;
                buttonImgUnClicked.scale(20, 30);
                buttonImgClicked.scale(20, 30);
                button = new Button(buttonImgUnClicked,buttonImgClicked) {
                    @Override
                    public void clicked() {
                        if (0 < inventoryTab) {
                            inventoryTab--;
                        } else {
                            inventoryTab = 2;
                        }
                    }
                };
                buttonList.add(button);
                world.addObject(button, 440,165);
            }else{
                buttonImgUnClicked = rightArrowNotClicked;
                buttonImgClicked = rightArrowClicked;
                buttonImgUnClicked.scale(20, 30);
                buttonImgClicked.scale(20, 30);
                button = new Button(buttonImgUnClicked,buttonImgClicked) {
                    @Override
                    public void clicked() {
                        if (0 < inventoryTab) {
                            inventoryTab--;
                        } else {
                            inventoryTab = 2;
                        }
                    }
                };
                buttonList.add(button);
                world.addObject(button, 780,165);
            }
        }
    public void deleteButtons(){
        for(Button button:buttonList){
            world.removeObject(button);
        }
    }


    public void mouseAreas(){
        //Inventar (7|6)
        int XX = 395;
        int YY = 195;
        InventoryScreen.setColor(Color.RED);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                InventoryScreen.fillRect(XX,YY, 52,52);
                XX = XX + 56 + 8;
            }
            XX = XX - 7*(56 + 8);
            YY = YY + 56 + 8;
        }
        XX = 194;
        YY = 190;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                InventoryScreen.fillRect(XX, YY, 52, 52);
                XX = XX + 56 + 8 + 14;
                if(i == 3){
                    return;
                }
            }
            XX = XX - 2 * (56 + 8 + 14);
            YY = YY + 56 + 18;
        }
    }

    //Getters and Setters
    public int getInventoryTab() {
        return inventoryTab;
    }
    public void setInventoryTab(int inventoryTab){
        this.inventoryTab = inventoryTab;
    }
}