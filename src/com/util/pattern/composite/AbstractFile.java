package com.util.pattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wn on 2018/2/24.
 */
public abstract class AbstractFile {
    public abstract void add(AbstractFile abstractFile);
    public abstract void look();
}

class VideoFile extends AbstractFile{
    String name;

    public VideoFile(String name) {
        this.name = name;
    }

    @Override
    public void add(AbstractFile abstractFile) {
        System.out.println("forbid this operate...");
    }

    @Override
    public void look() {
        System.out.println("look for video file..."+name);
    }
}
class ImageFile extends AbstractFile{
    String name;

    public ImageFile(String name) {
        this.name = name;
    }

    @Override
    public void add(AbstractFile abstractFile) {
        System.out.println("forbid this operate...");
    }

    @Override
    public void look() {
        System.out.println("look for image file..."+name);
    }
}
class Folder extends AbstractFile{
    String name;

    public Folder(String name) {
        this.name = name;
    }

    List<AbstractFile> files = new ArrayList<>();
    @Override
    public void add(AbstractFile abstractFile) {
        files.add(abstractFile);
    }

    @Override
    public void look() {
        System.out.println("look for  folder..."+name);
        for(AbstractFile file:files){
            file.look();
        }
    }

    public static void main(String[] args) {
        AbstractFile folder1,folder2,folder3,image1,image2,video1,video2;
        folder1 = new Folder("我的收藏");
        folder2 = new Folder("影像文件");
        folder3 = new Folder("图形文件");
        folder1.add(folder2);
        folder1.add(folder3);
        image1 = new ImageFile("1.png");
        image2 = new ImageFile("2.jpg");
        folder3.add(image1);
        folder3.add(image2);
        video1 = new VideoFile("1.mv");
        video2 = new VideoFile("2.avi");
        folder2.add(video1);
        folder2.add(video2);
        folder1.look();
    }
}

