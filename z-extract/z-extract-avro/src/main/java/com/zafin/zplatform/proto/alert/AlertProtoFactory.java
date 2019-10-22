package com.zafin.zplatform.proto.alert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.AvroRuntimeException;
import org.apache.avro.Schema.Field;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

import com.zafin.models.avro1.Alert;
import com.zafin.zplatform.proto.BuilderPopulator;
import com.zafin.zplatform.proto.PayLoad;
import com.zafin.zplatform.proto.PayLoadBase;
import com.zafin.zplatform.proto.PayLoadFactoryBase;
import com.zafin.zplatform.proto.ProtoFactoryBase;
import com.zafin.zplatform.proto.Schema;
import com.zafin.zplatform.proto.TransferState;
import com.zafin.zplatform.proto.exception.BuilderServiceException;
import com.zafin.zplatform.proto.factory.SchemaFactory;
import com.zafin.zplatform.proto.factory.TransferStateFactory;

public class AlertProtoFactory<T,B> extends ProtoFactoryBase<T,B> {
	
	
	public AlertProtoFactory(ProtoFactoryBuilder<T, B> builder) {
		super(builder);
	}

	/**
	 * The terminal case for a state transfer on 'first' revision just does not need to really transfer.
	 * However to just test state transfer for all cases this transfer instance will still go through 
	 * the state transfer process. This factory instance just passes out another instance of the same type.
	 * (Avro1.Alert to Avro1.Alert).
	 * 'First' revision just means the last backward compatible version. If there are [1...4] revisions and 
	 * revisions 1 & 2 are no longer backwards compatible, then the 'first' revision is 3. So the state transfer process
	 * of seeding state forward only needs to start at 3 and move that state forward to 4.
	 * @return
	 */
	public TransferStateFactory createTransferStateFactory(Class<?> oldSource, Class<?> newTarget) {
		return new TransferStateFactory() {
		}; 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static SchemaFactory<com.zafin.models.avro1.Alert> createSchemaFactory() {
		return new SchemaFactory() {

			@Override
			public Schema createSchema(Object object, int revision) {
				return null;
			}
		};
	}
	
	public static PayLoad createPayloadFrom(GenericRecordBuilder genericRecordBuilder,BuilderPopulator<?,?> populator) throws BuilderServiceException {
		GenericRecord limitedRecord = (GenericRecord) new LimitedBuilder(genericRecordBuilder).build();
		PayLoadFactoryBase<com.zafin.models.avro1.Alert> payloadFactory = new AlertPayLoadFactory1<>();
		PayLoadBase<com.zafin.models.avro1.Alert> payload = new PayLoadBase<>(payloadFactory);
				
		for (Field field:com.zafin.models.avro1.Alert.getClassSchema().getFields()) {
			payload.put(field.name(),limitedRecord.get(field.name()));
		}
		return payload;
    }
	//Limited in regards to enforcing default values
	static class LimitedBuilder extends GenericRecordBuilder {

		public LimitedBuilder(GenericRecordBuilder genericRecord) {
			super(genericRecord);
		}
		
		public Object defaultValue(Field field) throws IOException {
			try {
				return super.defaultValue(field);
			} catch (IOException|AvroRuntimeException ex) {
				return null;
			}
		}
	}
}
