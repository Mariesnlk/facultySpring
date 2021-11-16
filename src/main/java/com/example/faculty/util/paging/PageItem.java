package com.example.faculty.util.paging;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageItem {
    PageItemType pageItemType;
    int index;
    boolean active;
}
