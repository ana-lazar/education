import React, { useContext } from "react";
import {
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonList,
  IonFab,
  IonFabButton,
  IonIcon,
} from "@ionic/react";
import { getLogger } from "../core";
import { ProductContext } from "./ProductProvider";
import { RouteComponentProps } from "react-router";
import Product from "./Product";
import { add } from "ionicons/icons";

const log = getLogger("ProductList");

const ProductList: React.FC<RouteComponentProps> = ({ history }) => {
  const { products, deleteProduct } = useContext(ProductContext);
  log("render");
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Product list</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        {products && (
          <IonList inset={true} lines="full">
            {products.map(({ id, name, company, quantity }) => (
              <Product
                key={id}
                id={id}
                name={name}
                company={company}
                quantity={quantity}
                onEdit={(id) => id && history.push(`/product/${id}`)}
                onDelete={(id) => id && deleteProduct && deleteProduct(id)}
              />
            ))}
          </IonList>
        )}
        <IonFab vertical="bottom" horizontal="end" slot="fixed">
          <IonFabButton onClick={() => history.push("/product")}>
            <IonIcon icon={add} />
          </IonFabButton>
        </IonFab>
      </IonContent>
    </IonPage>
  );
};

export default ProductList;
