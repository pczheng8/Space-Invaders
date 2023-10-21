public interface MovableInterface {


    public abstract float getX();

    public abstract void setX(float x);

    public abstract float getY();

    public abstract void setY(float y);

    public abstract void setXY(float x, float y);

    public abstract int getWidth();

    public abstract void setWidth(int width);

    public abstract int getHeight();

    public abstract void setHeight(int height);

    public int getDirection();

    public void setDirection(int direction);


    public abstract float getTime();

    public abstract void setTime(float time);


    public abstract boolean getHidden();

    public abstract boolean isHidden();

    public abstract void setHidden(boolean hidden);


    public int getSpeedX();

    public void setSpeedX(int speedX);

    public int getSpeedY();

    public void setSpeedY(int speedY);

    public void setSpeedXY(int speedX, int speedY);


    public void setSpeed(int speed);

    public int getSpeed();


    public void setCloaked(boolean cloaked);

    public boolean getCloaked();

    public boolean isCloaked();


}