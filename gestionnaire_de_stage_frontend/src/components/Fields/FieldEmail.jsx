export default function FieldEmail({email,label,placeholder,handleChanges}) {
  return (
    <>
      <label className="label">{label}</label>
      <div className="input-group">
        <input className="form-control" placeholder={placeholder} type="email" name="email" value={email} onChange={handleChanges}/>
      </div>
    </>
  );
}
