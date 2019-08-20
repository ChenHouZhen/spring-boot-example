package com.chenhz.neo4j.utils;

import com.chenhz.neo4j.pojo.TreeNode;
import com.chenhz.neo4j.utils.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.xmind.core.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ImportXmind {

    private static Logger log = LoggerFactory.getLogger(ImportXmind.class);

    /**
     * 工作薄对象
     */
    private IWorkbook wb;


    private List<Validation> valids;


    public ImportXmind(MultipartFile multipartFile, Validation... valids)
            throws IOException, CoreException {
        IWorkbookBuilder builder = Core.getWorkbookBuilder();
        this.wb= builder.loadFromStream(multipartFile.getInputStream());
        // todo: 如果不传 valids，会不会报错？
        this.valids = Arrays.stream(valids).collect(Collectors.toList());
    }

    public TreeNode getData(){
        ITopic rootItopic = this.wb.getPrimarySheet().getRootTopic();
        String title = rootItopic.getTitleText();
        this.check(title);
        TreeNode rootNode = new TreeNode();
        rootNode.setTitle(trimTitle(title));
        this.next(rootItopic,rootNode);
        return rootNode;
    }


    private void next(ITopic fatherITopic,TreeNode fatherNode){

        List<ITopic> children = fatherITopic.getAllChildren();
        if (CollectionUtils.isEmpty(children)){
            return;
        }

        for (ITopic i: children){
            TreeNode n = new TreeNode();
            String title = i.getTitleText();
            this.check(title);
            n.setTitle(trimTitle(title));
            fatherNode.addChild(n);
            this.next(i,n);
        }
    }

    private void check(String title){
        for (Validation v:this.valids) {
            if (!v.check(title)){
                throw new IllegalArgumentException(v.msg());
            }
        }
    }

    private String trimTitle(String title){
        /*if (title != null){
            return title.trim();
        }
        return title;*/
        return title.trim();
    }

}
