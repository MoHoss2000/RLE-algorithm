
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import javax.imageio.ImageIO;

public class RunLengthCode {

	@SuppressWarnings("unused")
	private static void printArray(int[][] array) {
		for (int[] x : array) {
			for (int y : x) {
				System.out.print(y + " ");
			}
			System.out.println();
		}
	}

	private static boolean existsInArray(int[] array, int element) {
		for (int x : array) {
			if (x == element)
				return true;
		}
		return false;
	}

	private static String ComputeCode(int[][] imgArr) {
		String RLC = "";

		for (int i = 0; i < imgArr.length; i++) {
			int[] row = imgArr[i];
			if (existsInArray(row, 1)) {
				RLC += ("(" + i + " ");
				// 0 1 2 3 4 5 6 7 8 9
				// 0 0 0 1 1 1 0 1 1 1

				boolean flag = true;

				for (int j = 0; j < row.length; j++) {
					int pixel = row[j];
					if (pixel == 1) {
						if (flag) {
							RLC += (j + " ");
							flag = false;
						}

						if (j == row.length - 1 || row[j + 1] == 0) {
							flag = true;
							RLC += (j + " ");
						}
					}
					
					if(j == row.length - 1) {
						RLC = RLC.substring(0, RLC.length()-1);
						RLC += ")";
					}
				}
			}
		}

		return RLC;

	}

	public static void main(String[] args) {
		// write image path
//		String path = "binary_triangle.jpg";
		String path = "binary_line.jpg";

		BufferedImage image = null;

		try {

			File input_image = new File(path);
			// Reading input image
			image = ImageIO.read(input_image);
			System.out.println("Reading complete.");
		}

		catch (IOException e) {
			System.out.println(e);
		}

		// convert image to 2D array
		int width = image.getWidth();
		int height = image.getHeight();
		int[][] imgArr = new int[height][width];
		Raster raster = image.getData();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				imgArr[i][j] = raster.getSample(j, i, 0);
			}
		}
		
		// print run length Code
		System.out.println(ComputeCode(imgArr));

		String fileName = path.contains("binary_line.jpg") ? "Line_RLE.txt": "Triangle_RLE.txt";
		
		List<String> lines = Arrays.asList(ComputeCode(imgArr));

		Path file = Paths.get(fileName);
		try {
			Files.write(file, lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// main ends here

}// class ends here
