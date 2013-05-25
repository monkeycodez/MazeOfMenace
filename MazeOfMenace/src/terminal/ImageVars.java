/*******************************************************************************
 * Copyright (c) 2013 Matthew Gruda.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     matthew - initial API and implementation
 ******************************************************************************/
package terminal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageVars {

	private ImageVars() {
	}

	public static final Image floor = get1();
	public static Image floor2 = get2(), wall;
	public static Image wall2, stairup, stairup2, stairdown, stairdown2,
			fountain, fountain2;
	public static Image wallf, wallf2, floorf, floorf2, stairupf, stairupf2,
			stairdownf, stairdownf2, fountainf, fountainf2;
	public static Image playerf, goblinf, koboldf, orcf, orcwf, dragf, adragf, daemonf;
	public static Image armorf, swordf, orbf;
	static {
		try {
			wall = ImageIO.read(new File("./dat/tiles/static/wall.png"));
			wall2 = ImageIO.read(new File(
					"./dat/tiles/static/walloutofsight.png"));
			stairdown = ImageIO.read(new File(
					"./dat/tiles/static/stairdown.png"));
			stairdown2 = ImageIO.read(new File(
					"./dat/tiles/static/stairdownoutofsight.png"));
			stairup2 = ImageIO.read(new File(
					"./dat/tiles/static/stairupoutofsight.png"));
			stairup = ImageIO.read(new File("./dat/tiles/static/stairup.png"));
			fountain = ImageIO
					.read(new File("./dat/tiles/static/fountain.png"));
			fountain2 = ImageIO.read(new File(
					"./dat/tiles/static/fountainoutofsight.png"));
			wallf = ImageIO.read(new File("./dat/tiles/fancy/static/wall.png"));
			wallf2 = ImageIO.read(new File(
					"./dat/tiles/fancy/static/walloutofsight.png"));
			stairdownf = ImageIO.read(new File(
					"./dat/tiles/fancy/static/stairdown.png"));
			stairdownf2 = ImageIO.read(new File(
					"./dat/tiles/fancy/static/stairdownoutofsight.png"));
			stairupf2 = ImageIO.read(new File(
					"./dat/tiles/fancy/static/stairupoutofsight.png"));
			stairupf = ImageIO.read(new File(
					"./dat/tiles/fancy/static/stairup.png"));
			floorf = ImageIO
					.read(new File("./dat/tiles/fancy/static/floor.png"));
			floorf2 = ImageIO.read(new File(
					"./dat/tiles/fancy/static/flooroutofsight.png"));
			playerf = ImageIO.read(new File(
					"./dat/tiles/fancy/entity/player.png"));
			fountainf = ImageIO
					.read(new File("./dat/tiles/fancy/static/fountain.png"));
			fountainf2 = ImageIO.read(new File(
					"./dat/tiles/fancy/static/fountainoutofsight.png"));
			goblinf = ImageIO.read(new File(
					"./dat/tiles/fancy/entity/goblin.png"));
			koboldf = ImageIO.read(new File(
					"./dat/tiles/fancy/entity/kobold.png"));
			orcf = ImageIO.read(new File(
					"./dat/tiles/fancy/entity/orc.png"));
			orcwf = ImageIO.read(new File(
					"./dat/tiles/fancy/entity/orcwarlord.png"));
			armorf = ImageIO.read(new File(
					"./dat/tiles/fancy/entity/armor.png"));
			swordf = ImageIO.read(new File(
					"./dat/tiles/fancy/entity/sword.png"));
			orbf = ImageIO.read(new File(
					"./dat/tiles/fancy/entity/orb.png"));
			dragf = ImageIO.read(new File(
					"./dat/tiles/fancy/entity/dragon.png"));
			adragf = ImageIO.read(new File(
					"./dat/tiles/fancy/entity/olddragon.png"));
			daemonf = ImageIO.read(new File(
					"./dat/tiles/fancy/entity/daemon.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static Image get1() {
		try {
			return ImageIO.read(new File("./dat/tiles/static/floor.png"));
		} catch (Exception e) {
		}
		return null;
	}

	private static Image get2() {
		try {
			return ImageIO.read(new File(
					"./dat/tiles/static/flooroutofsight.png"));
		} catch (Exception e) {
		}
		return null;
	}

}
