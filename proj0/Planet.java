
/*
 * @File:   Planet.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/6 下午2:30
 * @Version:0.0
 */

public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String img) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = img;
    }

    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet planet) {
        return Math.sqrt((planet.xxPos - this.xxPos) * (planet.xxPos - this.xxPos) + (planet.yyPos - this.yyPos) * (planet.yyPos - this.yyPos));
    }

}
