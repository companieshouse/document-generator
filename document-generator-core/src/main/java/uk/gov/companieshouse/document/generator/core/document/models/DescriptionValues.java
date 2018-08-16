/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package uk.gov.companieshouse.document.generator.core.document.models;

import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class DescriptionValues extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -6491296529232799506L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"DescriptionValues\",\"namespace\":\"uk.gov.companieshouse.document.generator.core.document.models\",\"fields\":[{\"name\":\"date\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.String date;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public DescriptionValues() {}

  /**
   * All-args constructor.
   * @param date The new value for date
   */
  public DescriptionValues(java.lang.String date) {
    this.date = date;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return date;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: date = (java.lang.String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'date' field.
   * @return The value of the 'date' field.
   */
  public java.lang.String getDate() {
    return date;
  }

  /**
   * Sets the value of the 'date' field.
   * @param value the value to set.
   */
  public void setDate(java.lang.String value) {
    this.date = value;
  }

  /**
   * Creates a new DescriptionValues RecordBuilder.
   * @return A new DescriptionValues RecordBuilder
   */
  public static uk.gov.companieshouse.document.generator.core.document.models.DescriptionValues.Builder newBuilder() {
    return new uk.gov.companieshouse.document.generator.core.document.models.DescriptionValues.Builder();
  }

  /**
   * Creates a new DescriptionValues RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new DescriptionValues RecordBuilder
   */
  public static uk.gov.companieshouse.document.generator.core.document.models.DescriptionValues.Builder newBuilder(uk.gov.companieshouse.document.generator.core.document.models.DescriptionValues.Builder other) {
    return new uk.gov.companieshouse.document.generator.core.document.models.DescriptionValues.Builder(other);
  }

  /**
   * Creates a new DescriptionValues RecordBuilder by copying an existing DescriptionValues instance.
   * @param other The existing instance to copy.
   * @return A new DescriptionValues RecordBuilder
   */
  public static uk.gov.companieshouse.document.generator.core.document.models.DescriptionValues.Builder newBuilder(uk.gov.companieshouse.document.generator.core.document.models.DescriptionValues other) {
    return new uk.gov.companieshouse.document.generator.core.document.models.DescriptionValues.Builder(other);
  }

  /**
   * RecordBuilder for DescriptionValues instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<DescriptionValues>
    implements org.apache.avro.data.RecordBuilder<DescriptionValues> {

    private java.lang.String date;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(uk.gov.companieshouse.document.generator.core.document.models.DescriptionValues.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.date)) {
        this.date = data().deepCopy(fields()[0].schema(), other.date);
        fieldSetFlags()[0] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing DescriptionValues instance
     * @param other The existing instance to copy.
     */
    private Builder(uk.gov.companieshouse.document.generator.core.document.models.DescriptionValues other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.date)) {
        this.date = data().deepCopy(fields()[0].schema(), other.date);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'date' field.
      * @return The value.
      */
    public java.lang.String getDate() {
      return date;
    }

    /**
      * Sets the value of the 'date' field.
      * @param value The value of 'date'.
      * @return This builder.
      */
    public uk.gov.companieshouse.document.generator.core.document.models.DescriptionValues.Builder setDate(java.lang.String value) {
      validate(fields()[0], value);
      this.date = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'date' field has been set.
      * @return True if the 'date' field has been set, false otherwise.
      */
    public boolean hasDate() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'date' field.
      * @return This builder.
      */
    public uk.gov.companieshouse.document.generator.core.document.models.DescriptionValues.Builder clearDate() {
      date = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    public DescriptionValues build() {
      try {
        DescriptionValues record = new DescriptionValues();
        record.date = fieldSetFlags()[0] ? this.date : (java.lang.String) defaultValue(fields()[0]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
