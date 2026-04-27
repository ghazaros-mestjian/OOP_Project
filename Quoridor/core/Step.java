package core;

public class Step {
    public boolean isWall;
    public int direction;
    public boolean isVertical;
    public int x;
    public int y;

    public Step(String format) {
        String[] parts = format.trim().split(" ");
        if (parts.length == 1) {
            switch (parts[0]) {
                case "up":
                    direction = 1;
                case "down":
                    direction = 2;
                case "right":
                    direction = 3;
                case "left":
                    direction = 4;
                default:
                    break;
            }
            isWall = false;
        } else if (parts.length == 4) {
            if (!(parts[0].equals("wall")))
                return;
            switch (parts[1]) {
                case "v":
                    isVertical = true;
                case "h":
                    isVertical = false;
                default:
                    break; // throw
            }

            x = Integer.parseInt(parts[2]);
            y = Integer.parseInt(parts[3]);

            if (!Board.onBoard(x, y, isVertical))
                x = x + 9 - 9; // throw exception

        } else {
            x=x;
            //throw exception
        }

    }

}
