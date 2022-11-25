public class Game
{
    private Grid grid;
    private int y;
    private int x;
    private boolean lookingRight;
    private boolean lookingLeft;
    private Location player;
    private int key;
    private int msElapsed;
    private int timesGet;
    private int timesAvoid;
    public Game()
    {
        grid = new Grid(7, 16);
        //which row the user controlled image appears in
        x = 3;
        //which column the user controlled image appears in
        y = 0;
        //keeps track of total milliseconds since game started.
        msElapsed = 0;
        //keeps track of stuff you are supposed to acquire
        timesGet = 0;
        //keeps track of the total number of times the user has been hit by the things they're supposed to avoid
        timesAvoid = 0;
        //updates the title, and the score.
        updateTitle();
        grid.setImage(new Location(x, y), "bubbleBobbleRight.gif");
    }

    public void play()
    {
        while (!isGameOver()){
            grid.pause(100);
            handleKeyPress();
            bubbleShoot();
            if (msElapsed % 500 == 0)
            {
                scrollLeft();
                populateRightEdge();
            }
            updateTitle();
            msElapsed += 100;
        }
    }

    public void handleKeyPress(){
        key = grid.checkLastKeyPressed(); 
        //upArrow
        if(key == 38){
            x-=1;
            if(!outOfBounds(x, y)){
                grid.setImage(new Location(x, y), "bubbleBobbleRight.gif");
                grid.setImage(new Location(x+1, y), null);
            }else{
                x+=1;
                grid.setImage(new Location(x, y), "bubbleBobbleRight.gif");
            }
        }
        //downArrow
        if(key == 40){
            x+=1;
            if(!outOfBounds(x, y)){
                grid.setImage(new Location(x, y), "bubbleBobbleRight.gif");
                grid.setImage(new Location(x-1, y), null);
            }else{
                x-=1;
                grid.setImage(new Location(x, y), "bubbleBobbleRight.gif");
            }
        }
        //leftArrow
        if(key == 37){
            y-=1;
            lookingLeft = true;
            if(!outOfBounds(x, y)){
                grid.setImage(new Location(x, y), "bubbleBobbleRight.gif");    
                grid.setImage(new Location(x, y+1), null);
            }else{
                y+=1;
                grid.setImage(new Location(x, y), "bubbleBobbleRight.gif");
            }
        }
        //rightArrow
        if(key == 39){
            y+=1;
            lookingRight = true;
            if(!outOfBounds(x, y)){
                grid.setImage(new Location(x, y), "bubbleBobbleRight.gif");    
                grid.setImage(new Location(x, y-1), null);
            }else{
                y-=1;
                grid.setImage(new Location(x, y), "bubbleBobbleRight.gif");
            }
        }        
        //key==Z
        if(key == 90){
            if(!outOfBounds(x, y)){
                y+=1;
                grid.setImage(new Location(x, y), "bubbleLarge.gif");
                y-=1;
            }else{
                grid.setImage(new Location(x, y), "bubbleLarge.gif");
            }
        }
    }

    public void bubbleShoot(){

    }

    public boolean outOfBounds(int x, int y){
        boolean outOfBounds = false;
        if(x >= grid.getNumRows() || x < 0){
            outOfBounds = true; 
        }else if(y >= grid.getNumCols() || y < 0){
            outOfBounds = true;
        }
        return outOfBounds;
    }

    public void graphics(){

    }

    public void populateRightEdge(){
        int row = (int)(Math.random() * grid.getNumRows());
        int col = grid.getNumCols() - 1;
        grid.setImage(new Location(row, col), "bubbleLarge.gif");
    }

    public void scrollLeft(){
        handleCollision(new Location(x, y+1));
        for(int row = 0; row < grid.getNumRows(); row++){
            for(int col = 0; col < grid.getNumCols(); col++){
                String image1 = grid.getImage(new Location(row, col));
                if(image1 != null && image1 == "bubbleLarge.gif"){
                    grid.setImage(new Location(row, col), null);
                    if(!outOfBounds(row, col-1)){
                        grid.setImage(new Location(row, col-1), image1);
                    }else{
                        grid.setImage(new Location(row, col), null);
                    }
                }
            }
        }
        
    }

    public void handleCollision(Location loc){
        String s = grid.getImage(loc);
        if(s != null){
            grid.setImage(loc, null);
        }
    }

    public int getScore()
    {
        return 0;
    }


    public void updateTitle(){
        player = new Location(x, y);
        grid.setTitle("BubbleBobble:  " + player + " " + timesAvoid); 
    }

    public boolean isGameOver()
    {
        return false;
    }

    public static void test()
    {
        Game game = new Game();
        game.play();
    }

    public static void main(String[] args)
    {
        test();
    }
}