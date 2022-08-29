import {
  IonButton,
  IonButtons,
  IonContent,
  IonHeader,
  IonInput,
  IonItem,
  IonLabel,
  IonList,
  IonPage,
  IonTitle,
  IonToolbar,
  useIonToast,
} from "@ionic/react";
import React, { useContext, useState, useEffect } from "react";
import { ProductContext } from "./ProductProvider";
import { getLogger } from "../core";
import { RouteComponentProps } from "react-router";
import { ProductProps, validateProduct } from "./ProductProps";

const log = getLogger("ProductEdit");

interface ProductEditProps
  extends RouteComponentProps<{
    id?: string;
  }> {}

const ProductEdit: React.FC<ProductEditProps> = ({ history, match }) => {
  const { products, saveProduct } = useContext(ProductContext);
  const [product, setProduct] = useState<ProductProps>();
  const [present, dismiss] = useIonToast();
  useEffect(() => {
    log("useEffect");
    const routeId = match.params.id || "";
    const product = products?.find((p) => p.id === routeId);
    setProduct(product);
  }, [match.params.id, products]);
  const handleSave = () => {
    log("handleSave");
    const error = validateProduct(product);
    if (error === "") {
      const editedProduct = product ? { ...product } : {};
      saveProduct && saveProduct(editedProduct);
      present("Saved product", 3000);
      history.goBack();
    } else {
      present({
        buttons: [{ text: "hide", handler: () => dismiss() }],
        message: error
      });
    }
  };
  log("render");
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Product details</IonTitle>
          <IonButtons slot="start">
            <IonButton onClick={() => history.goBack()}>Back</IonButton>
          </IonButtons>
          <IonButtons slot="end">
            <IonButton onClick={handleSave}>Save</IonButton>
          </IonButtons>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        <IonList>
          <IonItem>
            <IonLabel position="stacked">Name</IonLabel>
            <IonInput
              value={product?.name}
              onIonChange={(e) =>
                setProduct({ ...product, name: e.detail.value || "" })
              }
            />
          </IonItem>
          <IonItem>
            <IonLabel position="stacked">Description</IonLabel>
            <IonInput
              value={product?.desc}
              onIonChange={(e) =>
                setProduct({ ...product, desc: e.detail.value || "" })
              }
            />
          </IonItem>
          <IonItem>
            <IonLabel position="stacked">Company</IonLabel>
            <IonInput
              value={product?.company}
              onIonChange={(e) =>
                setProduct({ ...product, company: e.detail.value || "" })
              }
            />
          </IonItem>
          <IonItem>
            <IonLabel position="stacked">Quantity</IonLabel>
            <IonInput
              type="number"
              inputMode="numeric"
              value={product?.quantity}
              onIonChange={(e) =>
                setProduct({
                  ...product,
                  quantity: e.detail.value ? parseInt(e.detail.value) : 0,
                })
              }
            />
          </IonItem>
          <IonItem>
            <IonLabel position="stacked">Category</IonLabel>
            <IonInput
              value={product?.category}
              onIonChange={(e) =>
                setProduct({ ...product, category: e.detail.value || "" })
              }
            />
          </IonItem>
          <IonItem>
            <IonLabel position="stacked">Isle</IonLabel>
            <IonInput
              value={product?.isle}
              onIonChange={(e) =>
                setProduct({ ...product, isle: e.detail.value || "" })
              }
            />
          </IonItem>
        </IonList>
      </IonContent>
    </IonPage>
  );
};

export default ProductEdit;
