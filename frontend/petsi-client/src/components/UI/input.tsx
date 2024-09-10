interface InputProps {
  type: "text" | "password";
  label: string;
  value: string;
  onChange: (value: string) => void;
}

const Input: React.FC<InputProps> = ({ type, label, value, onChange }) => {

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    onChange(e.target.value);
  };

  return (
    <div className="input-container">
      <label htmlFor={label.toLowerCase()}>{label}</label>
      <input
        id={label.toLowerCase()}
        type={type}
        value={value}
        onChange={handleChange}
        placeholder={`Enter your ${label.toLowerCase()}`}
        className="input-field"
      />
    </div>
  );
};

export default Input;
