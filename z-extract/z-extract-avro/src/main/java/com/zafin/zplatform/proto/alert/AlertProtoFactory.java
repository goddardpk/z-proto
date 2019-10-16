package com.zafin.zplatform.proto.alert;

import java.io.ByteArrayOutputStream;

import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

import com.zafin.models.avro1.Alert;
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
			private Class<?> SOURCE = oldSource;
			private Class<?> TARGET = newTarget;
		
			@Override
			public TransferState<?, ?> createTransferState(Object oldObject, Object newObject)
					throws BuilderServiceException {
				return new TransferState<com.zafin.models.avro1.Alert,com.zafin.models.avro1.Alert>() {

					@Override
					public Alert transferState(Object oldState) throws BuilderServiceException {
						
						if (!oldState.getClass().isAssignableFrom(SOURCE)) {
							throw new BuilderServiceException("Invalid argument: oldState argument must be type: [" + SOURCE.getCanonicalName() + "] not [" + oldState.getClass().getCanonicalName() + "].");
						}
						
						Alert newState = convert((Alert)oldState);
						if (!newState.getClass().isAssignableFrom(TARGET)) {
							throw new BuilderServiceException("Invalid argument: newState argument must be type: [" + TARGET.getCanonicalName() + "] not [" + newState.getClass().getCanonicalName() + "].");
						}
						return newState;
					}

					@Override
					public Class<?> getNewType() {
						return SOURCE;
					}

					@Override
					public Class<?> getOldType() {
						return TARGET;
					}
					//TODO: Trying out Avro hack to move state from one class to another
					private com.zafin.models.avro1.Alert convert(GenericRecord myRecord) throws BuilderServiceException {
						GenericDatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(com.zafin.models.avro1.Alert.getClassSchema());
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
						try {
							writer.write(myRecord, encoder);
							encoder.flush();

							byte[] avroData = out.toByteArray();
							out.close();

							SpecificDatumReader<com.zafin.models.avro1.Alert> reader = new SpecificDatumReader<com.zafin.models.avro1.Alert>(com.zafin.models.avro1.Alert.class);
							Decoder decoder = DecoderFactory.get().binaryDecoder(avroData, null);
							return reader.read(null, decoder);
						} catch (Exception e) {
							throw new BuilderServiceException("Unable to convert state.",e);
						}
					}
				};
			}};
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
}
