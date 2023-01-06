
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

    static double G = 6.67e-11;
    public double calcForceExertedBy(Planet planet) {
        double r = calcDistance(planet);
        return (G * planet.mass * this.mass) / (r * r);
    }

    public double calcForceExertedByX(Planet planet) {
        double force = calcForceExertedBy(planet);
        double r = calcDistance(planet);
        return (force * (planet.xxPos - this.xxPos)) / r;
    }

    public double calcForceExertedByY(Planet planet) {
        double force = calcForceExertedBy(planet);
        double r = calcDistance(planet);
        return (force * (planet.yyPos - this.yyPos)) / r;
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double countX = 0;
        for (Planet planet : planets) {
            if (this.equals(planet)) {
                continue;
            }
            countX += calcForceExertedByX(planet);
        }
        return countX;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double countY = 0;
        for (Planet planet : planets) {
            if (this.equals(planet)) {
                continue;
            }
            countY += calcForceExertedByY(planet);
        }
        return countY;
    }

    public double calcAcceleration(double force) {
       return force / this.mass;
    }

    public void update(double time, double xForce, double yForce) {
//        double velocityX = this.xxVel + time * calcAcceleration(xForce);
//        double velocityY = this.yyVel + time * calcAcceleration(yForce);
//        double positionX = this.xxPos + time * velocityX;
//        double positionY = this.yyPos + time * velocityY;
        this.xxVel += time * calcAcceleration(xForce);
        this.yyVel += time * calcAcceleration(yForce);
        this.xxPos += time * this.xxVel;
        this.yyPos += time * this.yyVel;
    }

}
