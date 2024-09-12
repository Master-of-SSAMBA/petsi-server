import { useNavigate } from "react-router-dom";
import * as St from "./Container.style";

interface ContainerProps {
    color: string;
    shadow: boolean;
    path?: string;
    children?: React.ReactNode;
}

const Container = (props: ContainerProps) => {
    const { color, shadow, path, children } = props;
    const navigate = useNavigate();

    const handleClick = () => {
        if (path) {
            navigate(path);
        }
    };

    return (
        <St.Container $color={color} $shadow={shadow} onClick={handleClick}>
            {children}
        </St.Container>
    );
};

export default Container;
