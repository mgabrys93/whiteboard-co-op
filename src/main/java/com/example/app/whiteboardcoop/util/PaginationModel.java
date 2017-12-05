package com.example.app.whiteboardcoop.util;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class PaginationModel {


    public List<Integer> generatePagesInterval(int page, int pageInterval, int pagesNumber){
        int minPageNumber = (page - pageInterval) > 0 ? page - pageInterval : 0;
        int maxPageNumber = (page + pageInterval) < pagesNumber ? page + pageInterval : pagesNumber;
        return IntStream.range(minPageNumber, maxPageNumber)
                .boxed()
                .collect(Collectors.toList());
    }

    public int getPageNumberRoundedUp(long elementCount, int pageElement){
        return (int) (elementCount % pageElement == 0 ? elementCount / pageElement : (elementCount / pageElement) + 1);
    }

    public Integer updatePageIfNull(Integer page){
        return page == null ? 1 : page;
    }

}
