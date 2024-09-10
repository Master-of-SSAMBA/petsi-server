import * as St from "./Input.style";

interface InputProps {
  type: "text" | "password";
  value: string;
  label: string;
  onChange: (value: string) => void;
}

const Input = (props: InputProps) => {
  const { type, value, label, onChange } = props;

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    onChange(e.target.value);
  };

  return (
    <St.InputContainer>
      <St.InputField
        type={type}
        value={value}
        onChange={handleChange}
        placeholder=""
        id={`input-${label}`}
      />
      <St.InputLabel htmlFor={`input-${label}`}>{label}</St.InputLabel>
    </St.InputContainer>
  );
};

export default Input;