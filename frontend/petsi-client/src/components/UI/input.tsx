interface InputProps {
  type: "text" | "password";
  label: string;
  value: string;
  onChange: (value: string) => void;
}

const Input = (props: InputProps) => {
  const { type, label, value, onChange } = props;

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    onChange(e.target.value);
  };

  return (
    <div className="input-container">
      input.tsx
      input.tsx
      input.tsx
      input.tsx
      input.tsx
      {/* <label htmlFor={label.toLowerCase()}>{label}</label>
      <input
        id={label.toLowerCase()}
        type={type}
        value={value}
        onChange={handleChange}
        placeholder={`Enter your ${label.toLowerCase()}`}
        className="input-field"
      /> */}
    </div>
  );
};

export default Input;
