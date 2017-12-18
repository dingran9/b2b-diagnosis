package com.eedu.diagnosis.manager.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图片处理
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-10-26 9:14
 **/
public class ImageUtils {
    /**
     * 分割图像
     *
     * @param image
     *            传入的图片对象
     * @param rows
     *            垂直方向上需要裁剪出的图片数量 - 行
     * @param cols
     *            水平方向上需要裁剪出的图片数量 - 列
     * @param x
     *            开始裁剪位置的X坐标
     * @param y
     *            开始裁剪位置的Y坐标
     * @param width
     *            每次裁剪的图片宽度
     * @param height
     *            每次裁剪的图片高度
     * @param changeX
     *            每次需要改变的X坐标数量
     * @param changeY
     *            每次需要改变的Y坐标数量
     * @param component
     *            容器对象，目的是用来创建裁剪后的每个图片对象
     * @return 裁剪完并加载到内存后的二维图片数组
     */
    public static Image[][] cutImage(Image image, int rows, int cols, int x,
                                     int y, int width, int height, int changeX, int changeY,
                                     Component component) {
        Image[][] images = new Image[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ImageFilter filter = new CropImageFilter(x + j * changeX, y + i
                                    * changeY, width, height);
                images[i][j] = component.createImage(new FilteredImageSource(
                                    image.getSource(), filter));
            }
        }

        return images;
    }




    /**
     * 指定大小进行缩放
     * 若图片横比width小，高比height小，不变 若图片横比width小，高比height大，高缩小到height，图片比例不变
     * 若图片横比width大，高比height小，横缩小到width，图片比例不变
     * 若图片横比width大，高比height大，图片按比例缩小，横为width或高为height
     *
     * @param source
     *            输入源
     * @param output
     *            输出源
     * @param width
     *            宽
     * @param height
     *            高
     */
    public static void ImgThumb(String source, String output, int width, int height) {
        try {
            Thumbnails.of(source).size(width, height).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定大小进行缩放
     *
     * @param source
     *            输入源
     * @param output
     *            输出源
     * @param width
     *            宽
     * @param height
     *            高
     */
    public static void ImgThumb(File source, String output, int width, int height) {
        try {
            Thumbnails.of(source).size(width, height).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按照比例进行缩放
     *
     * @param source
     *            输入源
     * @param output
     *            输出源
     */
    public static void ImgScale(String source, String output, double scale) {
        try {
            Thumbnails.of(source).scale(scale).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ImgScale(File source, String output, double scale) {
        try {
            Thumbnails.of(source).scale(scale).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 不按照比例，指定大小进行缩放
     *
     * @param source
     *            输入源
     * @param output
     *            输出源
     * @param width
     *            宽
     * @param height
     *            高
     * @param keepAspectRatio
     *            默认是按照比例缩放的,值为false 时不按比例缩放
     */
    public static void ImgNoScale(String source, String output, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ImgNoScale(File source, String output, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 旋转 ,正数：顺时针 负数：逆时针
     *
     * @param source
     *            输入源
     * @param output
     *            输出源
     * @param width
     *            宽
     * @param height
     *            高
     * @param rotate
     *            角度
     */
    public static void ImgRotate(String source, String output, int width, int height, double rotate) {
        try {
            Thumbnails.of(source).size(width, height).rotate(rotate).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ImgRotate(File source, String output, int width, int height, double rotate) {
        try {
            Thumbnails.of(source).size(width, height).rotate(rotate).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 水印
     *
     * @param source
     *            输入源
     * @param output
     *            输入源
     * @param width
     *            宽
     * @param height
     *            高
     * @param position
     *            水印位置 Positions.BOTTOM_RIGHT o.5f
     * @param watermark
     *            水印图片地址
     * @param transparency
     *            透明度 0.5f
     * @param quality
     *            图片质量 0.8f
     */
    public static void ImgWatermark(String source, String output, int width, int height, Position position, String watermark, float transparency, float quality) {
        try {
            Thumbnails.of(source).size(width, height).watermark(position, ImageIO.read(new File(watermark)), transparency).outputQuality(0.8f).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ImgWatermark(File source, String output, int width, int height, Position position, String watermark, float transparency, float quality) {
        try {
            Thumbnails.of(source).size(width, height).watermark(position, ImageIO.read(new File(watermark)), transparency).outputQuality(0.8f).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪图片
     *
     * @param source
     *            输入源
     * @param output
     *            输出源
     * @param position
     *            裁剪位置
     * @param x
     *            裁剪区域x
     * @param y
     *            裁剪区域y
     * @param width
     *            宽
     * @param height
     *            高
     * @param keepAspectRatio
     *            默认是按照比例缩放的,值为false 时不按比例缩放
     */
    public static void ImgSourceRegion(String source, String output, Position position, int x, int y, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).sourceRegion(position, x, y).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ImgSourceRegion(File source, String output, Position position, int x, int y, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).sourceRegion(position, x, y).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按坐标裁剪
     *
     * @param source
     *            输入源
     * @param output
     *            输出源
     * @param x
     *            起始x坐标
     * @param y
     *            起始y坐标
     * @param x1
     *            结束x坐标
     * @param y1
     *            结束y坐标
     * @param width
     *            宽
     * @param height
     *            高
     * @param keepAspectRatio
     *            默认是按照比例缩放的,值为false 时不按比例缩放
     */
    public static void ImgSourceRegion(String source, String output, int x, int y, int x1, int y1, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).sourceRegion(x, y, x1, y1).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ImgSourceRegion(File source, String output, int x, int y, int x1, int y1, int width, int height, boolean keepAspectRatio) {
        try {
            Thumbnails.of(source).sourceRegion(x, y, x1, y1).size(width, height).keepAspectRatio(keepAspectRatio).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转化图像格式
     *
     * @param source
     *            输入源
     * @param output
     *            输出源
     * @param width
     *            宽
     * @param height
     *            高
     * @param format
     *            图片类型，gif、png、jpg
     */
    public static void ImgFormat(String source, String output, int width, int height, String format) {
        try {
            Thumbnails.of(source).size(width, height).outputFormat(format).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ImgFormat(File source, String output, int width, int height, String format) {
        try {
            Thumbnails.of(source).size(width, height).outputFormat(format).toFile(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出到OutputStream
     *
     * @param source
     *            输入源
     * @param output
     *            输出源
     * @param width
     *            宽
     * @param height
     *            高
     * @return toOutputStream(流对象)
     */
    public static OutputStream ImgOutputStream(String source, String output, int width, int height) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(output);
            Thumbnails.of(source).size(width, height).toOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os;
    }

    public static OutputStream ImgOutputStream(File source, String output, int width, int height) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(output);
            Thumbnails.of(source).size(width, height).toOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return os;
    }

    /**
     * 输出到BufferedImage
     *
     * @param source
     *            输入源
     * @param output
     *            输出源
     * @param width
     *            宽
     * @param height
     *            高
     * @param format
     *            图片类型，gif、png、jpg
     * @return BufferedImage
     */
    public static BufferedImage ImgBufferedImage(String source, String output, int width, int height, String format) {
        BufferedImage buf = null;
        try {
            buf = Thumbnails.of(source).size(width, height).asBufferedImage();
            ImageIO.write(buf, format, new File(output));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf;
    }

    public static BufferedImage ImgBufferedImage(File source, String output, int width, int height, String format) {
        BufferedImage buf = null;
        try {
            buf = Thumbnails.of(source).size(width, height).asBufferedImage();
            ImageIO.write(buf, format, new File(output));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf;
    }

    public static void main(String[] args) {
        //选择题
        ImageUtils.ImgSourceRegion("E:\\testImg\\shuxue1.jpg","E:\\testImg\\shuxue2.jpg",257,2300,3547,690,800,800,true);
        //客观题ImageUtils.ImgSourceRegion("E:\\testImg\\shuxue1.jpg","E:\\testImg\\shuxue2.jpg",250,3100,3554,655,800,800,true);
    }
}
