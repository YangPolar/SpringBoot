package com.kinto.global.globalcouponbatch.Processor;

import com.kinto.global.globalcouponbatch.Model.SystemLogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * @author YangLiming
 */
public class SystemLogProcessor implements ItemProcessor<SystemLogModel, SystemLogModel> {
    private static final Logger log = LoggerFactory.getLogger(SystemLogProcessor.class);

    @Override
    public SystemLogModel process(final SystemLogModel systemLogModel) throws Exception {
        final String id = systemLogModel.getId().toUpperCase();
        final String operationType = systemLogModel.getOperation_type().toUpperCase();

        final SystemLogModel transformedPerson = new SystemLogModel(id, operationType);

        log.info("Converting (" + systemLogModel + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }
}
