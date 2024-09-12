import { useNavigate } from "react-router-dom";
import * as St from "./Button.style";

interface BtnProps {
    color: string;
    text: string;
    to?: string;
    shadow: boolean;
}

const Button = (props: BtnProps) => {
    const { color, text, to, shadow } = props;
    const navigate = useNavigate();

    const handleClick = () => {
        if (to) {
            navigate(to);
        }
    };

    return (
        <St.BtnContainer color={color} $shadow={shadow} onClick={handleClick}>
            <St.BtnText>{text}</St.BtnText>
        </St.BtnContainer>
    );
};

export default Button;
