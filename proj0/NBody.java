
/*
 * @File:   NBody.java
 * @Desc:
 * @Author: jacky
 * @Repo:   https://github.com/jackyliu16
 * @Date:   2023/1/6 下午7:25
 * @Version:0.0
 */


public class NBody {
    public static double readRadius(String str) {
        In in = new In(str);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String str) {
        In in = new In(str);
        int number_of_planet = in.readInt();
        in.readDouble();
        Planet[] planets = new Planet[number_of_planet];
        for ( int i = 0 ; i < number_of_planet ; i++ ) {
            double pX = in.readDouble();
            double pY = in.readDouble();
            double vX = in.readDouble();
            double vY = in.readDouble();
            double mass = in.readDouble();
            String imgFile = in.readString();
            planets[i] = new Planet(pX, pY, vX, vY, mass, imgFile);
        }
        return planets;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            return ;
        }
        // collect information
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String fileName = args[2];

        // get information
        double radius = readRadius(fileName);
        Planet[] planets = readPlanets(fileName);

        // set background image
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        StdDraw.picture(0, 0, "images/starfield.jpg");

        // Draw All the Planets
        for (Planet planet : planets) {
            StdDraw.picture(planet.xxPos, planet.yyPos, String.format("%s%s", "images/", planet.imgFileName));
        }

        StdDraw.show();
        StdDraw.pause(2000);
    }
}