package com.rgabay.neowrites.util;

import com.beust.jcommander.Parameter;
import lombok.Data;


/**
 * Created by rossgabay on 3/3/17.
 */
@Data
public class JCommanderSetup {

    @Parameter(names = "-l", description = "node loader type")
    private String nodeLoaderType;

    @Parameter(names =  "-t", description = "number of threads")
    private Integer threadNum;

    @Parameter(names = "-n", description = "nodes per thread")
    private Integer nodesNum;
}
